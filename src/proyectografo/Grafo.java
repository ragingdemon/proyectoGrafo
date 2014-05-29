package proyectografo;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.renderers.VertexLabelRenderer;
import java.awt.Dimension;
import java.awt.HeadlessException;

public class Grafo extends javax.swing.JFrame{
    private Graph<Persona, Cadena> grafo;

    public Grafo(Graph<Persona, Cadena> grafo, String title) throws HeadlessException {
        super(title);
        this.grafo = grafo;
        //Layout del grafo
        Layout<Persona, Cadena> layout = new CircleLayout(grafo);
        layout.setSize(new Dimension(300, 300));
        // BasicVisualizationServer<V,E> el objeto que se agrega a la ventana
        BasicVisualizationServer<Persona, Cadena> vv = new BasicVisualizationServer(layout);
        
        vv.setPreferredSize(new Dimension(400, 350));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());//labels para los vertices
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());//labels para las aristas
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);//posicion de las labelsde los vertices
        
        getContentPane().add(vv);
    }
    
    
}
