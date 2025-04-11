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
    var totalChips = 0
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
    private lateinit var availbleLocationsLabel: JLabel
    private lateinit var helpButton: JButton
    private lateinit var searchButton: JButton
    private lateinit var forwardButton: JButton
    private lateinit var backButton: JButton
    private lateinit var leftButton: JButton
    private lateinit var rightButton: JButton
    private lateinit var timeBackPanel: JPanel
    private lateinit var timeLevelPanel: JPanel
    private lateinit var pPopUp: PopUp


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


        locationLabel = JLabel("")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(15, 25, 320, 60)
        locationLabel.font = baseFont
        add(locationLabel)

        chipsLabel = JLabel("Chips Collected: ${app.totalChips}/5")
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
        availbleLocationsLabel.font = smallFont
        availbleLocationsLabel.bounds = Rectangle(360, 100, 220, 260)
        add(availbleLocationsLabel)

        helpButton = JButton("Help")
        helpButton.bounds = Rectangle(200, 50, 60, 50)
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
        locationLabel.text = app.currentLocation?.name + "⛇"
        descriptionLabel.text = "<html>Description: ${app.currentLocation?.description}"
        chipsLabel.text = "Chips Collected: ${app.totalChips}/5"

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
        // Show only if it has a height
        timeLevelPanel.isVisible = app.time > 1
        // Update the bar's size
        timeLevelPanel.bounds = Rectangle(0, 0, timeBackPanel.width, timeHeight)

        app.increaseTime()


        var availableLocationsText = "<html>Available Locations:<br>"

        if (app.currentLocation?.forward != null) availableLocationsText += "Forward: ${app.currentLocation?.forward?.name}<br>"
        if (app.currentLocation?.back != null) availableLocationsText += "Back: ${app.currentLocation?.back?.name}<br>"
        if (app.currentLocation?.left != null) availableLocationsText += "Left: ${app.currentLocation?.left?.name}<br>"
        if (app.currentLocation?.right != null) availableLocationsText += "Right: ${app.currentLocation?.right?.name}<br>"

        availbleLocationsLabel.text = availableLocationsText


        if (timeLevelPanel.height >= timeBackPanel.height) {
            this.isVisible = false
            pPopUp = PopUp(app, foundChip = false, true)
            pPopUp.isVisible = true
        }

        if (app.totalChips == 5 && app.currentLocation?.name == "Roulette Table") {
            pPopUp = PopUp(app, foundChip = false, lost = false, true)
            pPopUp.isVisible = true
        }

    }

    fun calcTimePanelHeight(): Int {
        val timeFraction = app.time.toDouble() / 10
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
                val foundChip = app.currentLocation?.hasChip == true
                app.currentLocation?.searched = true
                pPopUp = PopUp(app, foundChip)
                pPopUp.isVisible = true

                if (app.currentLocation?.hasChip == true)
                app.totalChips++
                app.currentLocation?.hasChip = false


            }

            helpButton -> {}
        }
        updateView()
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
        title = "Chip Pop-Up"
        if (lost) contentPane.preferredSize = Dimension(700,500)
        else contentPane.preferredSize = Dimension(400, 200)
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

        val instructions = JLabel("yadadadadadadada Instructions mumbo jumbo")
        instructions.bounds = Rectangle(25, 25, 350, 150)
        instructions.verticalAlignment = SwingConstants.TOP
        instructions.font = baseFont

        val foundChipMessage = JLabel("<html> Great Job you found a chip!")
        foundChipMessage.bounds = Rectangle(25, 25, 350, 150)
        foundChipMessage.verticalAlignment = SwingConstants.TOP
        foundChipMessage.font = baseFont


        val noFindChipMessage = JLabel("<html> Too bad no chip here!")
        noFindChipMessage.bounds = Rectangle(25, 25, 350, 150)
        noFindChipMessage.verticalAlignment = SwingConstants.TOP
        noFindChipMessage.font = baseFont

        val lostMessage = JLabel("<html> Oh no you didnt make it in time and another person won all da mone")
        lostMessage.bounds = Rectangle(25, 25, 350, 150)
        lostMessage.verticalAlignment = SwingConstants.TOP
        lostMessage.font = baseFont

        val askWonMessage = JLabel("<html> you have all of your chips! Do you want to play roulette?")
        askWonMessage.bounds = Rectangle(25, 25, 350, 150)
        askWonMessage.verticalAlignment = SwingConstants.TOP
        askWonMessage.font = baseFont

        val wonMessage = JLabel("<html>Wow great job you did it! you won all the money!")
        wonMessage.bounds = Rectangle(25, 25, 350, 150)
        wonMessage.verticalAlignment = SwingConstants.TOP
        wonMessage.font = baseFont

        val noButton = JButton("No!")
        noButton.bounds = Rectangle(150, 130, 100, 40)
        noButton.addActionListener { this.isVisible = false } // Closes the pop-up

        val doneButton = JButton("Done")
        doneButton.bounds = Rectangle(150, 130, 100, 40)
        doneButton.addActionListener{ exitProcess(0) }

        val yesButton = JButton("Yes!")
        yesButton.bounds = Rectangle(50, 130, 100, 40)
        yesButton.addActionListener {
            yesButton.isVisible = false
            noButton.isVisible = false
            askWonMessage.isVisible = false
            add(wonMessage)
            add(doneButton)

        } // Closes the pop-up



        val closeButton = JButton("Close")
        closeButton.bounds = Rectangle(150, 130, 100, 40)
        closeButton.addActionListener { this.isVisible = false } // Closes the pop-up





        if (foundChip && !lost && !winner) {
            add(foundChipMessage)
            add(closeButton)
        }

        if (!foundChip && !lost && !winner) {
            add(noFindChipMessage)
            add(closeButton)
        }

        // Add the lostMessage only if lost is true
        if (lost) {
            add(lostMessage)
            add(doneButton)
        }

        if (winner) {
                add(askWonMessage)
                add(yesButton)
                add(noButton)
        }



    }

}
//        }
//
//    }
//}


