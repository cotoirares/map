package Model.Type;

import Model.Value.Value;
import Model.Value.StringValue;

public class StringType implements Type {
    private String type = "String";

    @Override
    public boolean equals(Object another){
        if (another instanceof StringType)
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
        return new StringType();
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
