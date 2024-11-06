package Model.Type;

import Model.Value.Value;
import Model.Value.BoolValue;

public class BoolType implements Type{
    private String type = "bool";

    @Override
    public boolean equals(Object another){
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
