import java.util.*;

public class ExtremeFinder {
    public static void main(String[] args){
        System.out.print("Enter value n: ");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        ExtremeFinder e = new ExtremeFinder(num);
    }

    private Point[] list;
    private List<Point> extremes;

    public ExtremeFinder(int n){
        list = new Point[n];
        extremes = new LinkedList<>();

        generate(n);
        //printValues();
        long s = System.currentTimeMillis();
        obvious();
        long e = System.currentTimeMillis();
        System.out.println("Time for obvous Algoithm: " + (e-s) + "ms");
        //printValues();
        //printExtremes();

        extremes.clear();

        s = System.currentTimeMillis();
        better();
        e = System.currentTimeMillis();
        System.out.println("Time for better Algoithm: " + (e-s) + "ms");

        //printValues();
        //printExtremes();
    }
    public void generate(int n){
        Random rand = new Random();
        for(int i = 0; i < n; i++){
            int x = rand.nextInt(n+1) ;
            int y = rand.nextInt(n+1-x);
            Point z = new Point(x,y);
            list[i] = z;
        }
    }
    public void printValues(){
        for(int i = 0; i < list.length; i++){
            System.out.println(list[i].getX()+" : "+list[i].getY());
        }
    }
    public void printExtremes(){
        for(int i = 0; i < extremes.size();i++){
            System.out.println("Extreme "+ extremes.get(i).getX()+" : "+extremes.get(i).getY());
        }
    }
    public void better(){
        //Create a array of linkedlisteds
        List<Point>[] height = new LinkedList[list.length+1];
        //Initialize those ll with points that have -1 values
        for(int i = 0; i < height.length; i++){
            height[i] = new LinkedList<Point>();
            height[i].add(new Point(-1,-1));
        }
        //Find the max height(with respect to Y) for each X
        for(int i = 0; i < list.length; i++){
            if( list[i].getY() > height[list[i].getX()].get(0).getY()){
                height[list[i].getX()].clear();
                height[list[i].getX()].add(list[i]);
            }else if (list[i].getY() == height[list[i].getX()].get(0).getY()){
                height[list[i].getX()].add(list[i]);
            }

        }
        //Iterate backwards only adding points that are greater than the previous
        int step = -1;
        for(int i = height.length-1; i >= 0; i--){
            if(height[i].get(0).getY() > step){
                extremes.addAll(height[i]);
                step = height[i].get(0).getY();
            }
        }

    }
    public void obvious(){
        for(int i = 0; i < list.length; i++){
            boolean isExtreme = true;
            for(int j = 0; j < list.length; j++){
                if(j != i){
                    if((list[j].getX() > list[i].getX() &&  list[j].getY() >= list[i].getY()) || (list[j].getX() >= list[i].getX() && list[j].getY() > list[i].getY()))
                        isExtreme = false;
                }
            }
            if(isExtreme)
                extremes.add(list[i]);
        }
    }
    private class Point{
        private int x;
        private int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX(){ return x;}
        public int getY(){ return y;}
    }
}

