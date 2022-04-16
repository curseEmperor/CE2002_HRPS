package entities;

import Enums.PriceFilterType;

public class Node {
    PriceFilterType type;

    Number num;

    public Node(PriceFilterType type, Number num) {
        this.type = type;
        this.num = num;
    }

    public PriceFilterType getType() {
        return type;
    }

    public void setType(PriceFilterType type) {
        this.type = type;
    }

    public Number getNum() {
        return num;
    }

    public void setNum(Number num) {
        this.num = num;
    }

}
