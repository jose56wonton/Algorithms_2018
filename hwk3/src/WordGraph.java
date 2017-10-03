import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class WordGraph {
    private List<Vertex> vertexes;
    private List<Edge> edges;
    public WordGraph(List<String> words){
        vertexes = new ArrayList();
        edges = new ArrayList();
        for(int i = 0; i < words.size(); i++){
            vertexes.add(new Vertex(words.get(i)));
        }
        for(int i = 0; i < vertexes.size(); i++){
            for(int j = 0; j < vertexes.size(); j++){
                if(j != i ){
                    Vertex v1 = vertexes.get(i);
                    Vertex v2 = vertexes.get(j);
                    if(distance(v1.name,v2.name) < 2){
                        edges.add(new Edge(v1,v2));
                    }
                }
            }
        }
    }
    public static int distance(String a, String b) {
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
    private class Vertex {
        final private String name;


        public Vertex( String name) {

            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            //result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            Vertex other = (Vertex) obj;
            if (name == other.name)
                return true;
            else
                return false;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public static void main(String [] args){
        List<String> words = Arrays.asList("pain","gain","pan","span","gait","wait");

        WordGraph w = new WordGraph(words);
        System.out.print(w.edges.size());
        System.out.print(w.vertexes.size());




    }
}
