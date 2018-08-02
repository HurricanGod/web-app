package cn.hurrican.annotation;


import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/2
 * @Modified 17:22
 */
public interface SortedSetInstruct {

    Set<Integer> LEGAL_SET = Sets.newHashSet(-1, -2, -3, -4, -5, -6, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

    int ZRANGE = 1;
    int ZREVRANGE = -1;

    int ZRANGE_BY_SCORE_2PARAM = 2;
    int ZREVRANGE_BY_SCORE_2PARAM = -2;

    int ZRANGE_BY_SCORE_4PARAM = 3;
    int ZREVRANGE_BY_SCORE_4PARAM = -3;

    int ZRANGE_WITH_SCORES_2PARAM = 4;
    int ZREVRANGE_WITH_SCORES_2PARAM = -4;

    int ZRANGE_BY_SCORE_WITH_SCORES_2PARAM = 5;
    int ZREVRANGE_BY_SCORE_WITH_SCORES_2PARAM = -5;

    int ZRANGE_BY_SCORE_WITH_SCORES_4PARAM = 6;
    int ZREVRANGE_BY_SCORE_WITH_SCORES_4PARAM = -6;

    int ZRANGEBYLEX = 7;
    int ZRANGEBYLEX_2PARAM = 8;

    int ZRANK = 9;
    int ZREVRANK = -9;

    int ZCOUNT = 10;
    int ZSCORE = 11;
    int ZCARD = 12;
}
