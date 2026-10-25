import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BCityUI extends JFrame {
    private CardLayout layout;
    private JPanel cards;
    private Map<String, String> data = new HashMap<>();

    public BCityUI() {
        setTitle("Bangalore Smart City");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        cards = new JPanel(layout);

        loadData();

        cards.add(loginPanel(), "Login");
        cards.add(signUpPanel(), "SignUp");
        cards.add(mainPanel(), "Main");

        add(cards);
        layout.show(cards, "Login");
        setVisible(true);
    }

    private JPanel loginPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(52, 73, 94));

        JLabel title = new JLabel("Login");
        title.setBounds(420, 40, 300, 30);
        title.setFont(new Font("Verdana", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        p.add(title);

        JLabel uL = new JLabel("Username:"); uL.setBounds(330, 120, 100, 30); uL.setForeground(Color.WHITE);
        JTextField uF = new JTextField(); uF.setBounds(430, 120, 200, 30);
        JLabel pL = new JLabel("Password:"); pL.setBounds(330, 170, 100, 30); pL.setForeground(Color.WHITE);
        JPasswordField pF = new JPasswordField(); pF.setBounds(430, 170, 200, 30);

        JButton login = new JButton("Login"); login.setBounds(430, 230, 90, 30);
        login.addActionListener(e -> layout.show(cards, "Main"));
        JButton signup = new JButton("Sign Up"); signup.setBounds(540, 230, 90, 30);
        signup.addActionListener(e -> layout.show(cards, "SignUp"));

        p.add(uL); p.add(uF); p.add(pL); p.add(pF); p.add(login); p.add(signup);
        return p;
    }

    private JPanel signUpPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(44, 62, 80));

        JLabel title = new JLabel("Sign Up");
        title.setBounds(420, 40, 300, 30);
        title.setFont(new Font("Verdana", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        p.add(title);

        String[] lbls = {"Full Name:", "Email:", "Username:", "Password:"};
        JTextField[] flds = new JTextField[4];

        for (int i = 0; i < 4; i++) {
            JLabel l = new JLabel(lbls[i]); l.setForeground(Color.WHITE);
            l.setBounds(300, 120 + i * 50, 100, 30);
            flds[i] = (i == 3) ? new JPasswordField() : new JTextField();
            flds[i].setBounds(410, 120 + i * 50, 200, 30);
            flds[i].setBackground(new Color(236, 240, 241));
            p.add(l); p.add(flds[i]);
        }

        JButton create = new JButton("Create");
        create.setBounds(410, 330, 150, 30);
        create.addActionListener(e -> {
            JOptionPane.showMessageDialog(p, "Mock account created!");
            layout.show(cards, "Login");
        });
        p.add(create);
        return p;
    }

    private JPanel mainPanel() {
        JPanel main = new JPanel(new BorderLayout());

        JPanel nav = new JPanel();
        nav.setBackground(new Color(207, 207, 207));
        String[] tabs = {"Home", "Tourist", "Hotels", "Transport", "Map"};
        JPanel content = new JPanel(new CardLayout());

        for (String tab : tabs) {
            JButton b = new JButton(tab);
            b.setBackground(Color.WHITE);
            b.addActionListener(e -> ((CardLayout) content.getLayout()).show(content, tab));
            nav.add(b);
        }

        content.add(homePanel(), "Home");
        content.add(infoPanel("Tourist Places", "https://www.google.com/maps/search/tourist+places+in+bangalore"), "Tourist");
        content.add(infoPanel("Hotels", "https://www.google.com/maps/search/hotels+in+bangalore"), "Hotels");
        content.add(infoPanel("Transport", "https://www.google.com/maps/search/transport+services+in+bangalore"), "Transport");
        content.add(mapPanel(), "Map");

        main.add(nav, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        main.add(locationSearchPanel(), BorderLayout.SOUTH); // <-- Added input box here
        cards.add(main, "Main");
        return main;
    }

    private JPanel homePanel() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel("Welcome to Bangalore Smart City", JLabel.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 26));
        l.setForeground(new Color(0, 0, 0));
        p.setBackground(new Color(230, 190, 255));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private JPanel infoPanel(String title, String url) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(255, 253, 208));

        JTextArea area = new JTextArea(data.getOrDefault(title, "No data."));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setBackground(new Color(255, 255, 255));
        area.setForeground(new Color(44, 62, 80));

        JButton mapBtn = new JButton("Open Map");
        mapBtn.setBackground(new Color(52, 152, 219));
        mapBtn.setForeground(Color.WHITE);
        mapBtn.addActionListener(e -> {
            try { Desktop.getDesktop().browse(new URI(url)); }
            catch (Exception ex) { JOptionPane.showMessageDialog(p, "Error opening map."); }
        });

        JPanel south = new JPanel();
        south.setBackground(new Color(236, 240, 241));
        south.add(mapBtn);

        p.add(new JScrollPane(area), BorderLayout.CENTER);
        p.add(south, BorderLayout.SOUTH);
        return p;
    }

    private JPanel mapPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(189, 195, 199));
        JButton btn = new JButton("Open Bangalore Map");
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/Bangalore"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Unable to open map.");
            }
        });
        p.add(btn, BorderLayout.CENTER);
        return p;
    }

    private JPanel locationSearchPanel() {
        JPanel p = new JPanel();
        p.setBackground(new Color(236, 240, 241));

        JLabel label = new JLabel("Search Nearby: ");
        JTextField locationField = new JTextField(20);
        JButton searchBtn = new JButton("Show on Map");

        searchBtn.addActionListener(e -> {
            String input = locationField.getText().trim();
            if (!input.isEmpty()) {
                try {
                    String encoded = URLEncoder.encode("places near " + input, "UTF-8");
                    String url = "https://www.google.com/maps/search/" + encoded;
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(p, "Error opening map.");
                }
            } else {
                JOptionPane.showMessageDialog(p, "Please enter a location.");
            }
        });

        p.add(label);
        p.add(locationField);
        p.add(searchBtn);
        return p;
    }

    private void loadData() {
        data.put("Tourist Places", "Explore Bangalore's top attractions:\n\n- Lalbagh Botanical Garden: 240-acre garden with a glass house.\n- Cubbon Park: Peaceful park in the city center.\n- Bangalore Palace: Tudor-style palace with guided tours.\n- ISKCON Temple: Majestic spiritual center and vegetarian food.\n- Bannerghatta Biological Park: Safari rides and zoo experience.");

        data.put("Hotels", "Popular Hotels:\n\n- The Leela Palace: 5-star with luxury amenities.\n- Taj MG Road: Centrally located.\n- JW Marriott: Premium comfort.\n- ITC Gardenia: Eco-friendly luxury.\n- Oberoi: Known for gardens and service.");

        data.put("Transport", "Transport in Bangalore:\n\n- Namma Metro: ₹10 to ₹60 based on distance.\n- BMTC Buses: Affordable, ₹5-₹30.\n- Auto Rickshaws: Metered, starts at ₹30.\n- Ola/Uber: App-based, ₹100-₹300 depending on ride.\n- Airport Shuttle: ₹100-₹250 depending on stop.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BCityUI::new);
    }
}
