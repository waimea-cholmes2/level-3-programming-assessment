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
import kotlin.system.exitProcess


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
    var time = 1
    val maxTime = 50
    var totalChips = 0
    val totalChipsNeeded = 5
    lateinit var currentLocation: Location
    init {
        setUpMap()
    }

    class Location(val name: String, val description: String, var hasChip: Boolean, var searched: Boolean = false){
        var forward: Location? = null
        var left: Location? = null
        var right: Location? = null
        var back: Location? = null
    }

    /**
     * This function is used to setup the map of the game, where it defines each location and defines which locations are next to it.
     */
    fun setUpMap(){
        val roulette = Location("Roulette Table","Its like spinning with a ball", true)
        val blackjack = Location("Blackjack","Its got cards and stuff, people yelling hit and stuff", true)
        val elevator = Location("Elevator","Its got cards and stuff", true)
        val broomcloset = Location("Broom Closet","Its got cards and stuff", true)
        val entrance = Location("Entrance","Its got cards and stuff", true)

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

    /**
     * This function has the purpose of setting the players new currentLocation, I used a when loop in this to avoid a bunch of if else's
     */
    fun move(direction: String) {
        val nextLocation = when (direction) {
            "forward" -> currentLocation.forward
            "back" -> currentLocation.back
            "left" -> currentLocation.left
            "right" -> currentLocation.right
            else -> null
        }
        currentLocation = nextLocation!!
    }

    /**
     * This function increases the value time, which is used to calculate the height of the timeLevelBar
     */
    fun increaseTime() {
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
    private lateinit var descriptionTextLabel: JLabel
    private lateinit var availableLocationsLabel: JLabel
    private lateinit var availableLocationsTextLabel: JLabel
    private lateinit var helpButton: JButton
    private lateinit var searchButton: JButton
    private lateinit var forwardButton: JButton
    private lateinit var backButton: JButton
    private lateinit var leftButton: JButton
    private lateinit var rightButton: JButton
    private lateinit var timeBackPanel: JPanel
    private lateinit var timeLevelPanel: JPanel
    private lateinit var resultPopUp: PopUp


    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible
        updateView()                    // Initialise the UI
        InstructionsPopUp().isVisible = true // Display Instructions and Backstory
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
        val mediumSmallFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        val mediumFont = Font(Font.SANS_SERIF, Font.PLAIN, 30)

        //This says the name of the current location
        locationLabel = JLabel("")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(15, 25, 320, 60)
        locationLabel.font = baseFont
        add(locationLabel)
        //This shows how many chips have been collected by the player
        chipsLabel = JLabel("Chips Collected: ${app.totalChips}/5")
        chipsLabel.horizontalAlignment = SwingConstants.CENTER
        chipsLabel.bounds = Rectangle(360, 25, 270, 60)
        chipsLabel.font = mediumFont
        add(chipsLabel)
        //This is the label above the description of the current location
        descriptionTextLabel = JLabel("Description:")
        descriptionTextLabel.border = BorderFactory.createLineBorder(Color.white)
        descriptionTextLabel.horizontalAlignment = SwingConstants.CENTER
        descriptionTextLabel.bounds = Rectangle(25,100,280,30)
        descriptionTextLabel.font = mediumFont
        descriptionTextLabel.background = Color.DARK_GRAY
        add(descriptionTextLabel)
        //This is the description of the current location
        descriptionLabel = JLabel(app.currentLocation.description)
        descriptionLabel.border = BorderFactory.createLineBorder(Color.white)
        descriptionLabel.font = baseFont
        descriptionLabel.bounds = Rectangle(25, 130, 280, 230)
        add(descriptionLabel)

        availableLocationsTextLabel = JLabel("Available Locations:")
        availableLocationsTextLabel.font = mediumSmallFont
        availableLocationsTextLabel.border = BorderFactory.createLineBorder(Color.WHITE)
        availableLocationsTextLabel.horizontalAlignment = SwingConstants.CENTER
        availableLocationsTextLabel.bounds = Rectangle(360,100,220,30)
        add(availableLocationsTextLabel)

        availableLocationsLabel = JLabel()
        availableLocationsLabel.border = BorderFactory.createLineBorder(Color.white)
        availableLocationsLabel.font = mediumSmallFont
        availableLocationsLabel.bounds = Rectangle(360, 130, 220, 230)
        add(availableLocationsLabel)

        helpButton = JButton("Help")
        helpButton.bounds = Rectangle(610, 450, 60, 50)
        helpButton.addActionListener(this)
        add(helpButton)


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
        leftButton.bounds = Rectangle(380, 450, 50, 50)
        leftButton.font = mediumFont
        leftButton.addActionListener(this)     // Handle any clicks
        add(leftButton)

        rightButton = JButton("→")
        rightButton.bounds = Rectangle(520, 450, 50, 50)
        rightButton.font = mediumFont
        rightButton.addActionListener(this)     // Handle any clicks
        add(rightButton)

        timeBackPanel = JPanel()
        timeBackPanel.bounds = Rectangle(590, 100, 100, 300)
        timeBackPanel.border = BorderFactory.createLineBorder((Color.BLACK),7)
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
        locationLabel.text = app.currentLocation.name + "⛇"
        descriptionLabel.text = "<html>${app.currentLocation.description}"
        chipsLabel.text = "Chips Collected: ${app.totalChips}/5"

        forwardButton.isEnabled = app.currentLocation.forward != null
        backButton.isEnabled = app.currentLocation.back != null
        leftButton.isEnabled = app.currentLocation.left != null
        rightButton.isEnabled = app.currentLocation.right != null


        if (app.currentLocation.searched)
            searchButton.isEnabled = false
        else searchButton.isEnabled = true

        val timeHeight = calcTimePanelHeight()
        // Show only if it has a height
        timeLevelPanel.isVisible = app.time > 1
        // Update the bar's size
        timeLevelPanel.bounds = Rectangle(0, 0, timeBackPanel.width, timeHeight)

        app.increaseTime()


        var availableLocationsText = "<html>"

        if (app.currentLocation.forward != null) availableLocationsText += "Forward: ${app.currentLocation.forward?.name}<br>"
        if (app.currentLocation.back != null) availableLocationsText += "Back: ${app.currentLocation.back?.name}<br>"
        if (app.currentLocation.left != null) availableLocationsText += "Left: ${app.currentLocation.left?.name}<br>"
        if (app.currentLocation.right != null) availableLocationsText += "Right: ${app.currentLocation.right?.name}<br>"

        availableLocationsLabel.text = availableLocationsText


        if (timeLevelPanel.height >= timeBackPanel.height-7) {
            this.isVisible = false
            resultPopUp = PopUp(app, foundChip = false, true)
            resultPopUp.isVisible = true
        }

        if (app.totalChips == app.totalChipsNeeded && app.currentLocation.name == "Roulette Table") {
            resultPopUp = PopUp(app, foundChip = false, lost = false, true)
            resultPopUp.isVisible = true
            exitProcess(0)
        }

    }

    fun calcTimePanelHeight(): Int {
        val timeFraction = app.time.toDouble() / app.maxTime
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
            forwardButton -> {app.move("forward")
                updateView() }
            backButton -> {app.move("back")
                updateView() }
            leftButton -> {
                app.move("left")
                updateView() }
            rightButton -> {app.move("right")
                updateView() }

            searchButton -> {
                val foundChip = app.currentLocation.hasChip
                app.currentLocation.searched = true
                resultPopUp = PopUp(app, foundChip)
                resultPopUp.isVisible = true

                if (foundChip)
                app.totalChips++
                app.currentLocation.hasChip = false
                updateView()

            }

            helpButton -> {
                InstructionsPopUp().isVisible = true
            }
        }

    }


}
class PopUp(val app: App, val foundChip: Boolean = false, val lost: Boolean = false, val winner: Boolean = false, val instruct: Boolean = false) : JDialog() {
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
        title = "Pop-Up"
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

        val foundChipMessage = JLabel("<html> Great Job you found a chip!")
        foundChipMessage.bounds = Rectangle(25, 25, 350, 150)
        foundChipMessage.verticalAlignment = SwingConstants.TOP
        foundChipMessage.font = baseFont


        val noFindChipMessage = JLabel("<html> Too bad no chip here!")
        noFindChipMessage.bounds = Rectangle(25, 25, 350, 150)
        noFindChipMessage.verticalAlignment = SwingConstants.TOP
        noFindChipMessage.font = baseFont

        val lostMessage = JLabel("<html> Oh too bad! You took too long to find all of the chips and someone finally realised the ball kept landing on 17.")
        lostMessage.bounds = Rectangle(25, 25, 350, 150)
        lostMessage.verticalAlignment = SwingConstants.TOP
        lostMessage.font = baseFont

        val wonMessage = JLabel("<html>Wow great job you did it! you won all the money!")
        wonMessage.bounds = Rectangle(25, 25, 350, 150)
        wonMessage.verticalAlignment = SwingConstants.TOP
        wonMessage.font = baseFont

        val doneButton = JButton("Done")
        doneButton.bounds = Rectangle(150, 130, 100, 40)
        doneButton.addActionListener{ exitProcess(0) }

         // Closes the pop-up

        val closeButton = JButton("Close")
        closeButton.bounds = Rectangle(150, 130, 100, 40)
        closeButton.addActionListener { this.isVisible = false } // Closes the pop-up






        when {
            lost -> {
                add(lostMessage)
                add(doneButton)
            }
            foundChip -> {
                add(foundChipMessage)
                add(closeButton)
            }
            !foundChip && !winner && !lost -> {
                add(noFindChipMessage)
                add(closeButton)
            }
            winner -> {
                add(wonMessage)
                add(doneButton)
            }
        }



    }

}

class InstructionsPopUp(): JDialog() {
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
        title = "Instructions"
        contentPane.preferredSize = Dimension(400, 400)
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
        val message = JLabel("<html> <p><b>BACKSTORY:</b><br>\n" +
                "    You've rigged the roulette table using magnets to always land on 17... but tripped while carrying your chips! Now they've scattered across the casino.</p>\n" +
                "    \n" +
                "    <p><b>OBJECTIVE:</b><br>\n" +
                "    • Find all 5 lost chips before the casino closes<br>\n" +
                "    • Return to the roulette table to win your prize</p>\n" +
                "    \n" +
                "    <p><b>CONTROLS:</b><br>\n" +
                "    ↑↓←→ Move between areas<br>\n" +
                "    [SEARCH] Search the room for chips<br>\n" +
                "    [TIME BAR] Shows time left to find the chips</p>")
        message.bounds = Rectangle(25, 25, 350, 375)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)

        val closeButton = JButton("Close")
        closeButton.bounds = Rectangle(150, 330, 100, 40)
        closeButton.addActionListener { this.isVisible = false } // Closes the pop-up
        add(closeButton)
    }

}



