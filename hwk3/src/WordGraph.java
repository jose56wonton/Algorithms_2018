import java.util.*;

public class WordGraph {
    private List<Vertex> vertexes;
    private List<Edge> edges;
    public WordGraph(List<String> words){
        vertexes = new ArrayList();
        edges = new ArrayList();

        // Initialize Vertexes
        for(int i = 0; i < words.size(); i++){
            vertexes.add(new Vertex(words.get(i)));
        }
        // Initialize Edges
        for(int i = 0; i < vertexes.size(); i++){
            for(int j = 0; j < vertexes.size(); j++){
                if(j != i ){
                    Vertex v1 = vertexes.get(i);
                    Vertex v2 = vertexes.get(j);
                    if(distance(v1.name,v2.name) < 2){
                        if (!edges.contains(new Edge(v2,v1)) && !edges.contains(new Edge(v1,v2))){
                            edges.add(new Edge(v1,v2));
                        }
                    }
                }
            }
        }
        //Remove Duplicate Edges
        //removeEdgeRedundancy();
    }
    public int numberOfComponents(){
        return this.edges.size()/2;
    }
    public ArrayList<String> shortestPath(String word1, String word2){

        ArrayList<String> path = new ArrayList();
        Vertex source = this.findVertexByString(word1);
        computePaths(source);
        Vertex destination = this.findVertexByString(word2);
        for (Vertex vertex = destination; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex.getName());
        Collections.reverse(path);
        return path;
    }
    private Vertex findVertexByString(String s){
            for(Vertex v : this.getVertexes()) {
                if(v.getName()==(s)) {
                    return v;
                }
            }
            return null;
    }
    private void computePaths(Vertex source){
        source.setMinDistance(0.0);
        PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
        q.add(source);
        while (!q.isEmpty()) {
            Vertex u = q.poll();
            for(int i = 0; i < WordGraph.this.getEdges().size(); i++){
                if(WordGraph.this.getEdges().get(i).getSource().getName() == u.getName()){
                    Vertex v = WordGraph.this.getEdges().get(i).getDestination();
                    double distanceThroughU = u.getMinDistance() + 1;
                    if (distanceThroughU < v.getMinDistance()) {
                        q.remove(v);
                        System.out.println(distanceThroughU);
                        v.setMinDistance(distanceThroughU);
                        v.setPrevious(u);
                        q.add(v);
                    }
                }
            }
        }
    }
    private void removeEdgeRedundancy(){
        for(int i = 0; i < edges.size(); i++){
            for(int j = 0; j < edges.size(); j++){
                if(j != i ){
                    Edge e1 = edges.get(i);
                    Edge e2 = edges.get(j);
                    if(e1.source.name == e2.destination.name && e1.destination.name == e2.source.name) {
                        edges.remove(e2);
                    }
                }
            }
        }
    }
    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
    public List<Vertex> getVertexes() {
        return vertexes;
    }
    public List<Edge> getEdges() {
        return edges;
    }
    private class Edge  {
        private final Vertex source;
        private final Vertex destination;
        public Edge( Vertex source, Vertex destination) {

            this.source = source;
            this.destination = destination;
        }
        public Vertex getDestination() {
            return destination;
        }
        public Vertex getSource() {
            return source;
        }
        @Override
        public String toString() {
            return source + " " + destination;
        }
    }
    private class Vertex implements Comparable<Vertex> {
        final private String name;
        private double minDistance = Double.POSITIVE_INFINITY;
        private Vertex previous;
        public Vertex( String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public double getMinDistance(){
            return minDistance;
        }
        public void setMinDistance(double x){
            minDistance = x;
        }
        public Vertex getPrevious() {
            return previous;
        }
        public void setPrevious(Vertex previous) {
            this.previous = previous;
        }
        @Override
        public String toString() {
            return name;
        }
        @Override
        public int compareTo(Vertex other) {
            return Double.compare(getMinDistance(), other.getMinDistance());
        }
    }

    public static void main(String [] args){
        List<String> words = Arrays.asList("pain","gain","pan","span","gait","wait");
        WordGraph w = new WordGraph(words);
        System.out.print(w.shortestPath("pain","span"));

    }
}
