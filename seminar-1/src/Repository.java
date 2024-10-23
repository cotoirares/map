public class Repository implements IRepo{
    private IEnt[] array;
    private int current_pos;

    public Repository(int size){
        array = new IEnt[size];
        current_pos = 0;
    }

    public void WriteEntity(IEnt a) throws myException{
        if(current_pos >= array.length){
            throw new myException("Array is full");
        }
        array[current_pos] = a;
        current_pos++;
    }
    public IEnt[] getAll(){
        /*
        return a deep copy of array
        IEnt[] copy = new IEnt[current_pos];
        for(int i = 0; i < current_pos; i++){
            copy[i] = array[i];
        }
        return copy;
         */
        return null;
    }
}
