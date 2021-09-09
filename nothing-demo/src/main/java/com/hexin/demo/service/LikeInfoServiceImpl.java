package com.hexin.demo.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hexin.demo.constant.LikeTypeEnum;
import com.hexin.demo.constant.LikedStatusEnum;
import com.hexin.demo.constant.RedisKeyUtils;
import com.hexin.demo.entity.LikeCount;
import com.hexin.demo.entity.LikeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.hexin.demo.constant.RedisKeyUtils.*;


/**
 * @Author hex1n
 * @Date 2021/9/2 12:04
 * @Description
 */
@Service
@Slf4j
public class LikeInfoServiceImpl implements LikeInfoService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Long saveLiked2Redis(LikeInfo likeInfo) {
        checkLikeInfo(likeInfo);
        String script = "redis.call('HSET', KEYS[1],KEYS[3],ARGV[1]) return redis.call('hincrBy', KEYS[2],KEYS[4],ARGV[2])";
        LikeInfo insertLikeInfo = packageLikeInfo(likeInfo);
        Long count = executeLuaScriptAndGetCount(insertLikeInfo, script, 1);
        return count;
    }

    @Override
    public Long saveUnLike2Redis(LikeInfo likeInfo) {
        checkLikeInfo(likeInfo);
        String script = "local count=tonumber(redis.call('hget', KEYS[2],KEYS[4])) if type(count)=='number' and count>0 then redis.call('HSET', KEYS[1],KEYS[3],ARGV[1]) return redis.call('hincrBy', KEYS[2],KEYS[4],ARGV[2]) end return 0 ";
        LikeInfo insertLikeInfo = packageLikeInfo(likeInfo);
        Long count = executeLuaScriptAndGetCount(insertLikeInfo, script, -1);
        return count;
    }

    private Long executeLuaScriptAndGetCount(LikeInfo insertLikeInfo, String script, int increVal) {
        String likeInfoKey = getLikeInfoRedisKey(insertLikeInfo) + DELIMITER + insertLikeInfo.getOperationTime();
        DefaultRedisScript<Long> luaScript = new DefaultRedisScript<>();
        luaScript.setScriptText(script);
        luaScript.setResultType(Long.class);
        List<String> keys = Arrays.asList(LIKE_INFO_KEY, LIKE_INFO_COUNT_KEY, likeInfoKey, getLikeCountRedisKey(insertLikeInfo));
        Long count = (Long) redisTemplate.execute(luaScript, keys, insertLikeInfo, increVal);
        return count;
    }

    private LikeInfo packageLikeInfo(LikeInfo likeInfo) {
        LikeInfo insertLikeInfo = new LikeInfo();
        BeanUtils.copyProperties(likeInfo, insertLikeInfo);
        insertLikeInfo.setLikedType(LikeTypeEnum.getNameByVal(likeInfo.getLikedType()));
        insertLikeInfo.setOperationTime(System.currentTimeMillis() + "");
        return insertLikeInfo;
    }


    private String getLikeInfoRedisKey(LikeInfo likeInfo) {
        return RedisKeyUtils.getLikeKey(
                likeInfo.getLikedMemberId(),
                likeInfo.getLikedPostId(),
                likeInfo.getLikedType(), likeInfo.getLikedTypeId());
    }

    private String getLikeCountRedisKey(LikeInfo likeInfo) {
        return RedisKeyUtils.getLikeKey(
                likeInfo.getLikedMemberId(),
                null,
                likeInfo.getLikedType(), likeInfo.getLikedTypeId());
    }

    private void checkLikeInfo(LikeInfo likeInfo) {
        if (likeInfo == null) {
            throw new IllegalArgumentException("incoming likeInfo is null");
        }
        List<Integer> legalCodes = Arrays.asList(LikedStatusEnum.LIKE.getCode(), LikedStatusEnum.UNLIKE.getCode());
        if (!legalCodes.contains(likeInfo.getLikedStatus())) {
            throw new IllegalArgumentException("传入点赞参数不合法");
        }
        if (StringUtils.isAnyBlank(likeInfo.getLikedMemberId(), likeInfo.getLikedPostId(),
                likeInfo.getLikedType(), likeInfo.getLikedTypeId())) {
            throw new IllegalArgumentException("参数不能为空");
        }
    }

    @Override
    public List<LikeInfo> getLikedDataFromRedis() {
        List<LikeInfo> likeInfos = Lists.newArrayList();
        List<String> keys = Lists.newArrayList();
        Map data = redisTemplate.boundHashOps(LIKE_INFO_KEY).entries();
        if (data != null && data.size() > 0) {
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                keys.add(key);
                LikeInfo likeInfo = (LikeInfo) data.get(key);
                if (likeInfo != null) {
                    likeInfos.add(likeInfo);
                }
            }
        }
        // todo 插入数据库
        /**
         * 插入数据库成功后删除缓存中的记录----> 如果缓存删除失败/避免数据库重复插入数据,缓存数据的操作时间大于数据库中的最大一条数据时间才能插入
         */
        log.info("keys:{}", JSON.toJSONString(keys));
        for (String key : keys) {
            Long delete = redisTemplate.opsForHash().delete(LIKE_INFO_KEY, key);
            log.info("deleteCount:{}", delete);
        }
        return likeInfos;
    }

    @Override
    public List<LikeCount> getLikedCountFromRedis() {
        List<LikeCount> likeCounts = Lists.newArrayList();
        Map data = redisTemplate.boundHashOps(LIKE_INFO_COUNT_KEY).entries();
        if (data != null && data.size() > 0) {
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                String[] params = key.split(DELIMITER);
                LikeCount likeCount = new LikeCount();
                likeCount.setLikedMemberId(params[0]);
                likeCount.setLikedType(params[1]);
                likeCount.setLikedTypeId(params[2]);
                likeCount.setLikeTotal((Integer) data.get(key));
                likeCounts.add(likeCount);
            }
        }
        return likeCounts;
    }
}
