package loaders;

import com.google.common.io.Resources;
import model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TspLoader implements DataFileLoader<Node> {


    @Override
    public List<Node> load(String from) {
        URL url = Resources.getResource(from);
        Path path = null;
        try {
            path = Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try{
            return Files.readAllLines(path)
                    .stream()
                    .filter(line -> !line.isBlank())
                    .filter(line -> Character.isDigit(line.charAt(0)))
                    .map(this::loadNodeFromLine)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private Node loadNodeFromLine(String s) {
        String[] values = s.split(" ");
        int order = Integer.parseInt(values[0]);
        double x = Double.parseDouble(values[1]);
        double y = Double.parseDouble(values[2]);
        return new Node(order,x,y);
    }
}
