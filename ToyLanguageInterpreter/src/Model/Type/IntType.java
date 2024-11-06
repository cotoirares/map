package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {
    String type = "Int";

    @Override
    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public Value defaultValue(){
        return new IntValue(0);
    }
}
