package aie.easyAPI.core.structure;

import aie.easyAPI.interfaces.ITree;
import aie.easyAPI.models.ControllerRoutesMapping;

public class RouteTree implements ITree<Node<String>> {
    public Node<String> root;

    public RouteTree() {
        root = new Node<>();
        root.setValue("index");
    }

    public void add(ControllerRoutesMapping map) {

        if (map.getRoute().equalsIgnoreCase("index")) {
            Node<String> tmpNode = convertRoutesToSingleNode(map);
            if (!root.getNodes().isEmpty()) {
                tmpNode.nodes.addAll(root.nodes);
            }
            root = tmpNode;
        } else if (root.getNodes().isEmpty()) {
            Node<String> tmpNode = convertRoutesToSingleNode(map);
            root.nodes.add(tmpNode);
        } else {
            Node<String> startupNode = getStartupNode(root, map);
            if (startupNode == null) {
                Node<String> tmpNode = convertRoutesToSingleNode(map);
                root.getNodes().add(tmpNode);
            } else {
                handleControllerMap(startupNode, map);
            }
        }

    }

    //TODO: Not Ready Yet
    @Override
    public void remove(String route) {
        String[] subRoutes = route.split("/");
        Node<String> node = root;
        for (String subRoute : subRoutes) {
            node = searchForSubRoute(node, subRoute, null);
            if (node == null) {
                return;
            }
        }
    }

    @Override
    public ControllerRoutesMapping search(String route) {
        String[] subRoutes = route.split("/");
        ControllerRoutesMapping mapping = new ControllerRoutesMapping(null);
        Node<String> node = root;
        for (int i = 1; i < subRoutes.length; i++) {
            String subRoute = subRoutes[i];
            node = searchForSubRoute(node, subRoute, mapping);
            if (node == null) {
                return null;
            }
        }
        mapping.setMethodName(node.getMethodName());
        mapping.setHttpType(node.getHttpType());
        mapping.setMainClass(node.getControllerClass());
        return mapping;
    }

    private Node<String> convertRoutesToSingleNode(ControllerRoutesMapping mapping) {
        Node<String> mainNode = new Node<>();
        mainNode.setHttpType(mainNode.getHttpType());
        mainNode.setMethodName(mainNode.getMethodName());
        mainNode.setControllerClass(mainNode.getControllerClass());
        mainNode.setValue(mapping.getRoute());
        if (mapping.subLocations != null) {
            for (ControllerRoutesMapping map : mapping.subLocations) {
                mainNode.nodes.add(convertRoutesToSingleNode(map));
            }
        }
        if (mapping.getVariableRoutes() != null) {
            for (String s : mapping.getVariableRoutes()) {
                Node<String> singleNode = new Node<>();
                singleNode.setVariable(true);
                singleNode.setValue(s);
                singleNode.setHttpType(mainNode.getHttpType());
                singleNode.setMethodName(mainNode.getMethodName());
                singleNode.setControllerClass(mainNode.getControllerClass());
                mainNode.nodes.add(singleNode);
            }
        }
        return mainNode;
    }

    private Node<String> getStartupNode(Node<String> firstNode, ControllerRoutesMapping mapping) {
        Node<String> startNode = null;
        for (Node<String> node : firstNode.getNodes()) {
            if (node.getValue().equals(mapping.getRoute())) {
                startNode = node;
                break;
            }
        }
        return startNode;
    }

    private void handleControllerMap(Node<String> startupNode, ControllerRoutesMapping mapping) {

        for (ControllerRoutesMapping map : mapping.subLocations) {
            Node<String> tmpNode = getStartupNode(startupNode, map);
            if (tmpNode == null) {
                tmpNode = convertRoutesToSingleNode(map);
                root.getNodes().add(tmpNode);
            } else {
                handleControllerMap(tmpNode, map);
            }

        }
    }

    private Node<String> searchForSubRoute(Node<String> firstNode, String subRoute, ControllerRoutesMapping mapping) {
        if (firstNode.getNodes().isEmpty()) return null;

        for (Node<String> n : firstNode.getNodes()) {
            if (n.getValue().equals(subRoute)) {
                return n;
            } else if (n.isVariable()) {
                mapping.addVariableForMap(n.getValue(), subRoute);
                return n;
            }
        }
        return null;
    }
}
