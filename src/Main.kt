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
    var time = 10
    val MIN_TIME = 0
    var x = 0
    var currentLocation: Location? = null
    init {
        setUpMap()
    }

    class Location(val name: String, val description: String, var hasChip: Boolean, var searched: Boolean = false){
        var forward: Location? = null
        var left: Location? = null
        var right: Location? = null
        var back: Location? = null
    }

    fun setUpMap(){
        val roulette = Location("Roulette Table","Its like spinning with a ball", false)
        val blackjack = Location("Blackjack","Its got cards and stuff, people yelling hit and stuff", true)
        val elevator = Location("Elevator","Its got cards and stuff", false)
        val broomcloset = Location("Broom Closet","Its got cards and stuff", true)
        val entrance = Location("Entrance","Its got cards and stuff", false)

        roulette.forward = blackjack
        roulette.left = elevator
        roulette.right = broomcloset
        roulette.back = entrance

        elevator.right = roulette

        broomcloset.left = roulette

        entrance.forward = roulette

        blackjack.back = roulette

        currentLocation = roulette

    }


    fun move(direction: String) {
        val nextLocation = when (direction) {
            "forward" -> currentLocation?.forward
            "back" -> currentLocation?.back
            "left" -> currentLocation?.left
            "right" -> currentLocation?.right
            else -> null
        }
        currentLocation = nextLocation
    }

    fun decreaseTime() {
        time++
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
    private lateinit var chipPopUp: foundChipPopUp
    private lateinit var noChipPopUp: noFoundChipPopUp

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
        title = "Gambling Simulator"
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

        chipPopUp = foundChipPopUp()
        noChipPopUp = noFoundChipPopUp()

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

        availbleLocationsLabel = JLabel("<html>Available Locations:")
        availbleLocationsLabel.border = BorderFactory.createLineBorder(Color.white)
        availbleLocationsLabel.font = baseFont
        availbleLocationsLabel.bounds = Rectangle(360, 100, 220, 260)
        add(availbleLocationsLabel)


        searchButton = JButton("Search")
        searchButton.bounds = Rectangle(25, 400, 275, 100)
        searchButton.font = smallFont
        searchButton.addActionListener(this)     // Handle any clicks
        add(searchButton)

        forwardButton = JButton("↑")
        forwardButton.bounds = Rectangle(450, 380, 50, 50)
        forwardButton.font = mediumFont
        forwardButton.addActionListener(this)     // Handle any clicks
        add(forwardButton)

        backButton = JButton("↓")
        backButton.bounds = Rectangle(450, 450, 50, 50)
        backButton.font = mediumFont
        backButton.addActionListener(this)     // Handle any clicks
        add(backButton)

        leftButton = JButton("←")
        leftButton.bounds = Rectangle(370, 450, 50, 50)
        leftButton.font = mediumFont
        leftButton.addActionListener(this)     // Handle any clicks
        add(leftButton)

        rightButton = JButton("→")
        rightButton.bounds = Rectangle(530, 450, 50, 50)
        rightButton.font = mediumFont
        rightButton.addActionListener(this)     // Handle any clicks
        add(rightButton)

        timeBackPanel = JPanel()
        timeBackPanel.bounds = Rectangle(590, 100, 100, 400)
        timeBackPanel.border = BorderFactory.createLineBorder(Color.BLACK)
        timeBackPanel.background = Color.YELLOW
        timeBackPanel.layout = null                // Want layout to be manual
        add(timeBackPanel)

        timeLevelPanel = JPanel()
        timeLevelPanel.bounds = Rectangle(0, 0, 100, 0)
        timeLevelPanel.background = Color.BLACK
        timeBackPanel.add(timeLevelPanel)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        locationLabel.text = app.currentLocation?.name
        descriptionLabel.text = "<html>Description: ${app.currentLocation?.description}"
        chipsLabel.text = "Chips Collected: ${app.x}/5"

        if (app.currentLocation?.forward != null)
            forwardButton.isEnabled = true
        else forwardButton.isEnabled = false

        if (app.currentLocation?.back != null)
            backButton.isEnabled = true
        else backButton.isEnabled = false

        if (app.currentLocation?.left != null)
            leftButton.isEnabled = true
        else leftButton.isEnabled = false

        if (app.currentLocation?.right != null)
            rightButton.isEnabled = true
        else rightButton.isEnabled = false

        if (app.currentLocation?.searched == true)
            searchButton.isEnabled = false
        else searchButton.isEnabled = true

        val timeHeight = calcTimePanelHeight()
        timeLevelPanel.bounds = Rectangle(0, timeBackPanel.height - timeHeight, 100, timeHeight)
        // Show only if it has a height
        if (app.time > 10)
            timeLevelPanel.isVisible = true
        else timeLevelPanel.isVisible = false




        // Update the bar's size
        timeLevelPanel.bounds = Rectangle(0, 0, timeBackPanel.width, timeHeight)

        app.decreaseTime()
    }

    fun calcTimePanelHeight(): Int {
        val timeFraction = app.time.toDouble() / 100
        val maxHeight = timeBackPanel.bounds.height   // Background panel's height
        return (maxHeight * timeFraction).toInt()     // Calculate height dynamically
    }


    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            forwardButton -> app.move("forward")
            backButton -> app.move("back")
            leftButton -> app.move("left")
            rightButton -> app.move("right")

            searchButton -> {
                app.currentLocation?.searched = true
                if (app.currentLocation?.hasChip == true) {
                chipPopUp.isVisible = true
                app.x++
                app.currentLocation?.hasChip = false
                }
                else noChipPopUp.isVisible = true


            }
        }
        updateView()
    }

    class foundChipPopUp() : JDialog() {
        /**
         * Configure the UI
         */
        init {
            configureWindow()
            addControls()
            setLocationRelativeTo(null)     // Centre the window
        }

        /**
         * Setup the dialog window
         */
        private fun configureWindow() {
            title = "Chip Pop-Up"
            contentPane.preferredSize = Dimension(400, 200)
            isResizable = false
            isModal = true
            layout = null
            pack()
        }

        /**
         * Populate the window with controls
         */
        private fun addControls() {
            val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

            // Adding <html> to the label text allows it to wrap
            val message = JLabel("<html>OMG you found la coino")
            message.bounds = Rectangle(25, 25, 350, 150)
            message.verticalAlignment = SwingConstants.TOP
            message.font = baseFont
            add(message)

            val closeButton = JButton("Close")
            closeButton.bounds = Rectangle(150, 130, 100, 40)
            closeButton.addActionListener { this.isVisible = false } // Closes the pop-up
            add(closeButton)
        }

    }

    class noFoundChipPopUp() : JDialog() {
        /**
         * Configure the UI
         */
        init {
            configureWindow()
            addControls()
            setLocationRelativeTo(null)     // Centre the window
        }

        /**
         * Setup the dialog window
         */
        private fun configureWindow() {
            title = "Chip Pop-Up"
            contentPane.preferredSize = Dimension(400, 200)
            isResizable = false
            isModal = true
            layout = null
            pack()
        }

        /**
         * Populate the window with controls
         */
        private fun addControls() {
            val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

            // Adding <html> to the label text allows it to wrap
            val message = JLabel("<html>OMG you didn't find la coino")
            message.bounds = Rectangle(25, 25, 350, 150)
            message.verticalAlignment = SwingConstants.TOP
            message.font = baseFont
            add(message)

            val closeButton = JButton("Close")
            closeButton.bounds = Rectangle(150, 130, 100, 40)
            closeButton.addActionListener { this.isVisible = false } // Closes the pop-up
            add(closeButton)
        }

    }
}


