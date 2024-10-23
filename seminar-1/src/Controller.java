public class Controller {
    IRepo repo;
    public Controller(IRepo repo){
        this.repo = repo;
    }
    public void addEntity(IEnt a) throws myException{
        try {
            repo.WriteEntity(a);
        }
        catch (myException e){
            throw new myException(e.getMessage());
        }
    }
    IEnt computeHeaviest(){
        IEnt[] all = repo.getAll();
        IEnt max = all[0];
        for(int i = 1; i < all.length; i++){
            if(all[i].getWeight() > max.getWeight()){
                max = all[i];
            }
        }
        return max;
    }
}
