package cn.xie.goods.page;

/**
 * 表达式类
 * Created by baobiao on 2017/3/10.
 */
public class Expression {
    private String name;//名称
    private String operator;//运算符
    private String value;//值

    public Expression() {
    }

    public Expression(String name, String operator, String value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "name='" + name + '\'' +
                ", operator='" + operator + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
