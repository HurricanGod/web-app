package cn.hurrican.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/10/18
 * @Modified 17:54
 */
@Data
@Accessors(chain = true)
public class Questionnaire {

    private Integer id;

    /**
     * 活动ID
     */
    private Integer aid;

    /**
     * 所属商家id
     */
    private Integer uid;

    /**
     * 游戏模板ID
     */
    private Integer pageTemplateId;

    /**
     * 0:文字题; 1:图片题;
     */
    private Integer questionType;

    /**
     * 0:单选;1:多选
     */
    private Integer optionType;

    /**
     * 所属领域
     */
    private String tag;

    /**
     * 问题详情
     */
    private String questionContent;

   /** 答案选项 **/
    private List<String> optionList = new ArrayList<>(1);

    /**
     * 可以用于存放每个选项分值等信息
     */
    private List<Entry<String, String>> scoreList = new ArrayList<>(1);

    /**
     * 答案是否必填
     */
    private Boolean answerNotBlank;

    /**
     * 扩展，可用于排序等功能
     */
    private Integer showOrder;

    /**
     * 是否被标记位删除
     */
    private Boolean hadDeleted;

    /**
     * 最后更改时间
     */
    private Date lastUpdateTime;

    /**
     * json字符串扩展
     */
    private String extendString;





}
