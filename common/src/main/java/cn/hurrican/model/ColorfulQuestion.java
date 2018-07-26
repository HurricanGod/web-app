package cn.hurrican.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/26
 * @Modified 10:26
 */
@Data
@ToString
@Accessors(chain = true)
public class ColorfulQuestion {

    public static ColorfulQuestion newInstance() {
        return new ColorfulQuestion();
    }

    private Integer id;

    /**
     * 活动ID
     * <p>
     * Table:     t_recreation_colorful_question
     * Column:    aid
     * Nullable:  true
     */
    private Integer aid;

    /**
     * 账户ID
     * <p>
     * Table:     t_recreation_colorful_question
     * Column:    uid
     * Nullable:  true
     */
    private Integer uid;

    /**
     * 平台ID，默认为有娱平台
     * <p>
     * Table:     t_recreation_colorful_question
     * Column:    platform_id
     * Nullable:  true
     */
    private Integer platformId;

    /**
     * 游戏模板ID
     * <p>
     * Table:     t_recreation_colorful_question
     * Column:    page_template_id
     * Nullable:  true
     */
    private Integer pageTemplateId;

    /**
     * 题目类型
     */
    private Integer type;

    private Entry<String, String> question;

    private List<Entry<String, String>> candidateAnswer;

    private Integer rightIndex;

    /**
     * 回答正确文案显示
     */
    private Entry<String, String> correctShow;

    /**
     * 回答错误文案显示
     */
    private Entry<String, String> wrongShow;

    /**
     * 其他文案显示
     */
    private String otherShow;

    /**
     * 扩展字段
     */
    private String extend;
}
