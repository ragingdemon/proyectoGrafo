package proyectografo;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
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
import java.util.LinkedHashMap;
import java.util.Map;
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
//        VisualizationViewer<V,E> el objeto que muestra el grafo
        VisualizationViewer<Persona, Cadena> vv = new VisualizationViewer(layout);

        vv.setPreferredSize(new Dimension(400, 350));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());//labels para los vertices
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());//labels para las aristas
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);//posicion de las labels de los vertices
        //añadir eventos del mouse al grafo
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        //Añadir botones para buscar los vertices de mayor importancia
        JButton button1 = new JButton("Vertices mayor cantidad de aristas");
        JButton button2 = new JButton("Vertices importantes por cercania");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importaciaAristas(e);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importanciaCercania(e);
            }
        });

        getContentPane().add(vv, java.awt.BorderLayout.CENTER);
        getContentPane().add(button1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(button2, java.awt.BorderLayout.PAGE_START);
    }

    private void importaciaAristas(ActionEvent evt) {
        ArrayList<Persona> personas = new ArrayList(grafo.getVertices());
        if (personas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "no hay personas");
            return;
        }
        int edgeCount = 0;
        ArrayList<Persona> resultado = new ArrayList();
        for (Persona persona : personas) {

            if (grafo.degree(persona) == edgeCount) {
                resultado.add(persona);
            } else if (grafo.degree(persona) > edgeCount) {
                edgeCount = grafo.degree(persona);
                resultado.clear();
                resultado.add(persona);
            }
        }
        if (edgeCount != 0) {
            System.out.println("los vértices más importantes son: " + resultado);
        }
    }

    private void importanciaCercania(ActionEvent evt) {
        ArrayList<Persona> personas = new ArrayList(grafo.getVertices());
        if (personas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "no hay personas");
            return;
        }
        ArrayList<Persona> resultado = new ArrayList();
        LinkedHashMap<Persona, Integer> totalDistanceMap = new LinkedHashMap();
        int minDistance = 0;
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            DijkstraShortestPath<Persona, Cadena> alg = new DijkstraShortestPath(grafo);
            Map<Persona, Number> ShortestPathMap = alg.getDistanceMap(persona);
            ArrayList<Number> values = new ArrayList(ShortestPathMap.values());
            int totalDistancePersona = 0;
            for (Number number : values) {
                totalDistancePersona += number.intValue();
            }
            if (totalDistancePersona != 0) {
                if (minDistance == 0) {
                    minDistance = totalDistancePersona;
                } else if (minDistance > totalDistancePersona) {
                    minDistance = totalDistancePersona;
                }
                totalDistanceMap.put(persona, totalDistancePersona);
            }
        }
        System.out.println(totalDistanceMap);

        for (Persona persona : personas) {
            try {
                if (totalDistanceMap.get(persona) == minDistance) {
                    resultado.add(persona);
                }
            } catch (Exception e) {
            }
        }
        System.out.println("los vértices más importantes son: " + resultado);
    }
}
