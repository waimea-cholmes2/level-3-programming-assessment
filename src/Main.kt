/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   PROJECT NAME HERE
 * Project Author: Cooper Holmes
 * GitHub Repo:    https://github.com/waimea-cholmes2/level-3-programming-assessment
 * ---------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * =====================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Constants defining any key values
    var x = 0

    class Location(val name: String, val description: String, ){
        val neighbors: MutableList<Pair<String, Location>> = mutableListOf()
    }

    class GameMap {
        val locations: MutableList<Location> = mutableListOf()
        var currentLocation: Location? = null

        private fun setUpMap(){
            // I like apples and banana's
        }

    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var locationLabel: JLabel
    private lateinit var chipsLabel: JLabel
    private lateinit var descriptionLabel: JLabel
    private lateinit var availbleLocationsLabel: JLabel
    private lateinit var searchButton: JButton
    private lateinit var collectButton: JButton
    private lateinit var forwardButton: JButton
    private lateinit var backButton: JButton
    private lateinit var leftButton: JButton
    private lateinit var rightButton: JButton
    private lateinit var timeBackPanel: JPanel
    private lateinit var timeLevelPanel: JPanel

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Project"
        contentPane.preferredSize = Dimension(700, 550)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 40)
        val smallFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        val mediumFont = Font(Font.SANS_SERIF, Font.PLAIN, 30)

        locationLabel = JLabel("Location Name")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(15, 25, 320, 60)
        locationLabel.font = baseFont
        add(locationLabel)

        chipsLabel = JLabel("Chips Collected: ${app.x}/5")
        chipsLabel.horizontalAlignment = SwingConstants.CENTER
        chipsLabel.bounds = Rectangle(360, 25, 270, 60)
        chipsLabel.font = mediumFont
        add(chipsLabel)

        descriptionLabel = JLabel("Description:")
        descriptionLabel.border = BorderFactory.createLineBorder(Color.white)
        descriptionLabel.font = baseFont
        descriptionLabel.bounds = Rectangle(25, 100, 280, 260)
        add(descriptionLabel)

        availbleLocationsLabel = JLabel("Available Locations:")
        availbleLocationsLabel.border = BorderFactory.createLineBorder(Color.white)
        availbleLocationsLabel.font = baseFont
        availbleLocationsLabel.bounds = Rectangle(360, 100, 220, 260)
        add(availbleLocationsLabel)


        searchButton = JButton("Search")
        searchButton.bounds = Rectangle(25,400,100,100)
        searchButton.font = smallFont
        searchButton.addActionListener(this)     // Handle any clicks
        add(searchButton)

        collectButton = JButton("Collect")
        collectButton.bounds = Rectangle(200,400,100,100)
        collectButton.font = smallFont
        collectButton.addActionListener(this)     // Handle any clicks
        add(collectButton)

        forwardButton = JButton("↑")
        forwardButton.bounds = Rectangle(450,380,50,50)
        forwardButton.font = mediumFont
        forwardButton.addActionListener(this)     // Handle any clicks
        add(forwardButton)

        backButton = JButton("↓")
        backButton.bounds = Rectangle(450,450,50,50)
        backButton.font = mediumFont
        backButton.addActionListener(this)     // Handle any clicks
        add(backButton)

        leftButton = JButton("←")
        leftButton.bounds = Rectangle(370,450,50,50)
        leftButton.font = mediumFont
        leftButton.addActionListener(this)     // Handle any clicks
        add(leftButton)

        rightButton = JButton("→")
        rightButton.bounds = Rectangle(530,450,50,50)
        rightButton.font = mediumFont
        rightButton.addActionListener(this)     // Handle any clicks
        add(rightButton)

        timeBackPanel = JPanel()
        timeBackPanel.bounds = Rectangle(590, 100, 100, 400)
        timeBackPanel.border = BorderFactory.createLineBorder(Color.BLACK)
        timeBackPanel.background = Color.gray
        timeBackPanel.layout = null                // Want layout to be manual
        add(timeBackPanel)

        timeLevelPanel = JPanel()
        timeLevelPanel.bounds = Rectangle(0, 0, 100, 300)
        timeLevelPanel.background = Color.DARK_GRAY
        timeBackPanel.add(timeLevelPanel)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {

        }
    }

}

