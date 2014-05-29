package proyectografo;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.renderers.VertexLabelRenderer;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Grafo extends javax.swing.JFrame {

    private Graph<Persona, Cadena> grafo;

    public Grafo(Graph<Persona, Cadena> grafo, String title) throws HeadlessException {
        super(title);
        this.grafo = grafo;
        //Layout del grafo
        Layout<Persona, Cadena> layout = new CircleLayout(grafo);
        layout.setSize(new Dimension(300, 300));
        // BasicVisualizationServer<V,E> el objeto que muestra el grafo
        VisualizationViewer<Persona, Cadena> vv = new VisualizationViewer(layout);

        vv.setPreferredSize(new Dimension(400, 350));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());//labels para los vertices
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());//labels para las aristas
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);//posicion de las labelsde los vertices
        //añadir eventos del mouse al grafo
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        //Añadir boton para buscar los vertices con la mayor cantidad de aristas
        JButton button1 = new JButton("Vertices mayo cantidad de aristas");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        
        //setLayout(null);
        getContentPane().add(vv,java.awt.BorderLayout.CENTER);
        getContentPane().add(button1,java.awt.BorderLayout.PAGE_END);
    }

    private void button1ActionPerformed(ActionEvent evt) {
        ArrayList<Persona> personas = new ArrayList(grafo.getVertices());
        if (personas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "no hay personas");
            return;
        }
        int vertexCount = 0;
        ArrayList<Persona> resultado = new ArrayList();
        for (Persona persona : personas) {

            //ArrayList<Cadena> cadenas = new ArrayList(grafo.getIncidentEdges(persona));
            if (grafo.degree(persona) == vertexCount) {
                resultado.add(persona);
            } else if (grafo.degree(persona) > vertexCount) {
                vertexCount = grafo.degree(persona);
                resultado.clear();
                resultado.add(persona);
            }
        }
        System.out.println("los que mas tiene son: " + resultado);
    }
}
