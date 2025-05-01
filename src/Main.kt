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
        // create all the locations
        val roulette = Location("Roulette Table","you see crowds of people and smoke covering the table, watching the ball roll continuously", false)
        val blackjack = Location("Blackjack","A green-felt blackjack table with numbered betting spots, a card shoe, dealer chip rack, surrounded by flashing lights and ambient casino noise", false)
        val elevator = Location("Elevator","A tight and compact space, some nice music though, surely no chip in here", false)
        val entrance = Location("Entrance","Big glass doors with a red carpet floor, everywhere you see enormous signs pointing to all of the games", false)
        val slotMachines = Location("Slot Machines","all you can see is machine after machine after machine, each with a person throwing away money", false)
        val horseRaces = Location("Horse Races","A massive screen completely covers the back wall, displaying all the odds for each horse, then there's just rows and rows of filled seats", true)
        val frontDesk = Location("Front Desk","A nice lady greets you as you walk close, the marble desk she stands behind shines your reflection right back at you", false)
        val mainStage = Location("The Main Stage","An enormous stage lit by lights as tall as they ceiling, people are sat, waiting for some kind of convention", false)
        val bar = Location("The Bar","A man sits behind a bar, cleaning a glass though there is no one around, tables sit empty, lots of room for the chips to have fallen", true)
        val atms = Location("The ATMS","A small corner in the casino yet it is still well decorated, there are a few people waiting to withdraw more of their hard earned money", false)
        val office = Location("The Office","You poke your head through a door and find yourself in a waiting room, filled with people who wish to complain about loosing their money", false)
        val poker = Location("Poker Table","This table is almost silent, still each seat is full and all of the people here give you a hard look as you get near, like they want you to leave ", true)
        val sportsBetting = Location("Sports Betting","Here you find one bookie behind a desk, he looks at you expectantly, you choose to avoid eye contact", false)
        val lostAndFound = Location("Lost and Found","You see a small hole in the wall, occupied by a teenager who sits on his phone, not even paying attention", false)
        val giftShop = Location("The Gift Shop","A nice little store with a few employees who seem to just be cleaning up a mess from a fallen vase", false)
        val bathroom = Location("The Bathroom","Once inside it is clear that this bathroom has a very nice design, but the people who use it dont really care for cleanliness", false)
        val upstairsEntrance = Location("The Upstairs Entrance","You walk out of the elevator into a well lit hallway with marble floors, a sign reading lounge sits in front of  you", false)
        val lounge = Location("The Lounge","Finally you reach a place where no one is gambling, instead everyone is sitting and talking in the well lit room", false)
        val terrace = Location("The Rooftop Terrace","You walk out into a small garden with nice flowers, and look over the city, now beaming with light", true)
        val upstairsBathroom = Location("The Upstairs Bathroom","This bathroom is much nicer than the downstairs one, the same design, but is has actually been kept clean", false)
        val arcade = Location("The Arcade","A large room filled with arcade games from the 80's, you wonder what the purpose of this room is, because no one is here.", false)
        val janitorsCloset = Location("Janitors Closet","A tight and compact room, filled with cleaning products and toiletries", false)
        val storageCloset = Location("Storage Closet","A more moderately sized room, filled with food, drinks and frozen goods, but there is a way to the attic", false)
        val attic = Location("The Attic","Once you've got up the ladder into the attic all you can see are cobwebs and old boxes, you could hear a pin drop", true)

        //define all the locations that the user can move to from each location
        roulette.forward = blackjack
        roulette.left = frontDesk
        roulette.right = elevator
        roulette.back = bathroom

        bathroom.forward = roulette

        blackjack.forward = slotMachines
        blackjack.left = mainStage
        blackjack.right = giftShop
        blackjack.back = roulette

        giftShop.left = blackjack
        giftShop.back = elevator

        slotMachines.forward = horseRaces
        slotMachines.left = bar
        slotMachines.back = blackjack

        horseRaces.left = atms
        horseRaces.back = slotMachines

        entrance.forward = frontDesk

        frontDesk.forward = mainStage
        frontDesk.left = office
        frontDesk.right = roulette
        frontDesk.back = entrance

        mainStage.forward = bar
        mainStage.left = poker
        mainStage.right = blackjack
        mainStage.back = frontDesk

        bar.forward = atms
        bar.left = sportsBetting
        bar.right = slotMachines
        bar.back = mainStage

        atms.left = lostAndFound
        atms.right = horseRaces
        atms.back = bar

        office.forward = poker
        office.right = frontDesk

        poker.forward = sportsBetting
        poker.right = mainStage
        poker.back = office

        sportsBetting.forward = lostAndFound
        sportsBetting.right = bar
        sportsBetting.back = poker

        lostAndFound.right = atms
        lostAndFound.back = sportsBetting

        elevator.forward = giftShop
        elevator.left = roulette
        elevator.right = upstairsEntrance

        upstairsEntrance.forward = lounge
        upstairsEntrance.left = elevator

        lounge.forward = arcade
        lounge.left = terrace
        lounge.right = upstairsBathroom
        lounge.back = upstairsEntrance

        terrace.right = lounge

        upstairsBathroom.left = lounge

        arcade.forward = storageCloset
        arcade.right = janitorsCloset
        arcade.back = lounge

        janitorsCloset.left = arcade

        storageCloset.forward = attic
        storageCloset.back = arcade

        attic.back = storageCloset

        //set starting point at the roulette table
        currentLocation = roulette

    }

    /**
     * This function has the purpose of setting the players new currentLocation,
     * I used a when loop in this to avoid a bunch of if/else statements,
     * and it passes in the direction value which defines the players next location
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
    private lateinit var timeLabel: JLabel
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
        title = "High Stakes"
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
        val averageFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        val mediumFont = Font(Font.SANS_SERIF, Font.PLAIN, 30)

        //This says the name of the current location
        locationLabel = JLabel("")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(15, 5, 320, 100)
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
        descriptionTextLabel.font = averageFont
        descriptionTextLabel.background = Color.BLACK
        add(descriptionTextLabel)
        //This is the description of the current location
        descriptionLabel = JLabel(app.currentLocation.description)
        descriptionLabel.border = BorderFactory.createLineBorder(Color.white)
        descriptionLabel.font = smallFont
        descriptionLabel.bounds = Rectangle(25, 130, 280, 230)
        add(descriptionLabel)
        //This is the Label above the available locations
        availableLocationsTextLabel = JLabel("Available Locations:")
        availableLocationsTextLabel.font = averageFont
        availableLocationsTextLabel.border = BorderFactory.createLineBorder(Color.WHITE)
        availableLocationsTextLabel.horizontalAlignment = SwingConstants.CENTER
        availableLocationsTextLabel.bounds = Rectangle(360,100,220,30)
        availableLocationsTextLabel.background = Color.BLACK
        add(availableLocationsTextLabel)
        //This is the label which shows all available locations
        availableLocationsLabel = JLabel()
        availableLocationsLabel.border = BorderFactory.createLineBorder(Color.white)
        availableLocationsLabel.font = averageFont
        availableLocationsLabel.bounds = Rectangle(360, 130, 220, 230)
        add(availableLocationsLabel)
        //This button opens the instructions pop up
        helpButton = JButton("Help")
        helpButton.bounds = Rectangle(610, 450, 60, 50)
        helpButton.addActionListener(this)
        add(helpButton)
        //This button searches the current location for a chip
        searchButton = JButton("Search")
        searchButton.bounds = Rectangle(25, 400, 275, 100)
        searchButton.font = smallFont
        searchButton.addActionListener(this)     // Handle any clicks
        add(searchButton)
        //This button moves the player forwar
        forwardButton = JButton("↑")
        forwardButton.bounds = Rectangle(450, 380, 50, 50)
        forwardButton.font = mediumFont
        forwardButton.addActionListener(this)     // Handle any clicks
        add(forwardButton)
        //This button moves the player back
        backButton = JButton("↓")
        backButton.bounds = Rectangle(450, 450, 50, 50)
        backButton.font = mediumFont
        backButton.addActionListener(this)     // Handle any clicks
        add(backButton)
        //This button moves the player left
        leftButton = JButton("←")
        leftButton.bounds = Rectangle(380, 450, 50, 50)
        leftButton.font = mediumFont
        leftButton.addActionListener(this)     // Handle any clicks
        add(leftButton)
        //This button moves the player right
        rightButton = JButton("→")
        rightButton.bounds = Rectangle(520, 450, 50, 50)
        rightButton.font = mediumFont
        rightButton.addActionListener(this)     // Handle any clicks
        add(rightButton)
        //This is the panel that the level panel slides down
        timeBackPanel = JPanel()
        timeBackPanel.bounds = Rectangle(590, 100, 100, 300)
        timeBackPanel.border = BorderFactory.createLineBorder((Color.GRAY),7)
        timeBackPanel.background = Color.YELLOW
        timeBackPanel.layout = null                // Want layout to be manual
        add(timeBackPanel)
        //This panel slides down the back panel as each move is made
        timeLevelPanel = JPanel()
        timeLevelPanel.bounds = Rectangle(0, 0, 100, 0)
        timeLevelPanel.background = Color.GRAY
        timeBackPanel.add(timeLevelPanel)

        timeLabel = JLabel("TIME")
        timeLabel.bounds = Rectangle(590,100,100,100)
        timeLabel.horizontalAlignment = SwingConstants.CENTER
        timeLabel.font = averageFont
        timeLabel.foreground = Color.BLACK
        add(timeLabel)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        //rest all info about the game
        locationLabel.text = "<html>" + app.currentLocation.name
        descriptionLabel.text = "<html>${app.currentLocation.description}"
        chipsLabel.text = "Chips Collected: ${app.totalChips}/5"
        //Check if each button has a corresponding location
        forwardButton.isEnabled = app.currentLocation.forward != null
        backButton.isEnabled = app.currentLocation.back != null
        leftButton.isEnabled = app.currentLocation.left != null
        rightButton.isEnabled = app.currentLocation.right != null

        //Check if the room has been searched
        searchButton.isEnabled = !app.currentLocation.searched

        //Define the new height of the timeLevelPanel
        val timeHeight = calcTimePanelHeight()
        // Show only if it has a height
        timeLevelPanel.isVisible = app.time > 1
        // Update the bar's size
        timeLevelPanel.bounds = Rectangle(0, 0, timeBackPanel.width, timeHeight)

        app.increaseTime()

        //Change what locations that are available that are shown to the player
        var availableLocationsText = "<html>"

        if (app.currentLocation.forward != null) availableLocationsText += "Forward: ${app.currentLocation.forward?.name}<br>"
        if (app.currentLocation.back != null) availableLocationsText += "Back: ${app.currentLocation.back?.name}<br>"
        if (app.currentLocation.left != null) availableLocationsText += "Left: ${app.currentLocation.left?.name}<br>"
        if (app.currentLocation.right != null) availableLocationsText += "Right: ${app.currentLocation.right?.name}<br>"

        availableLocationsLabel.text = availableLocationsText

        //Check if the player has died(ran out of time), the minus 7 is to accomodate for the thickness of the border on the backpanel
        if (timeLevelPanel.height >= timeBackPanel.height-7) {
            //if the player is dead display the won message
            this.isVisible = false
            resultPopUp = PopUp(app, foundChip = false, true)
            resultPopUp.isVisible = true
        }
        //Check if the player has won the game(collect all the chips and made it back to the roulette table)
        if (app.totalChips == app.totalChipsNeeded && app.currentLocation.name == "Roulette Table") {
            resultPopUp = PopUp(app, foundChip = false, lost = false, true)
            resultPopUp.isVisible = true
            exitProcess(0)
        }

    }

    /**
     * This functions purpose is tio calculate the height of the timelevel panel,
     * this function expects to return and integer
     */
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
            //Perform moves based on which movement button is pressed, passing the direction to the move function
            forwardButton -> {app.move("forward")
                updateView() }
            backButton -> {app.move("back")
                updateView() }
            leftButton -> {
                app.move("left")
                updateView() }
            rightButton -> {app.move("right")
                updateView() }
            //Opening a pop-up to say whether a chip was found
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
            //Opening the instructions if the player forgot anything
            helpButton -> {
                InstructionsPopUp().isVisible = true
            }
        }

    }


}

/**
 * This popup class is used to create all pop-ups other than the instructions pop up
 * the value for the class are passed in when the pop-up is called and are used to determine what is
 * displayed on the pop-up
 */
class PopUp(val app: App, val foundChip: Boolean = false, val lost: Boolean = false, val winner: Boolean = false) : JDialog() {
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

        //Creating all the messages and buttons for all the different pop-ups

        val foundChipMessage = JLabel("<html> Great Job you found a chip!")
        foundChipMessage.bounds = Rectangle(25, 25, 350, 150)
        foundChipMessage.verticalAlignment = SwingConstants.TOP
        foundChipMessage.font = baseFont


        val noFoundChipMessage = JLabel("<html> Too bad no chip here!")
        noFoundChipMessage.bounds = Rectangle(25, 25, 350, 150)
        noFoundChipMessage.verticalAlignment = SwingConstants.TOP
        noFoundChipMessage.font = baseFont

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





        //deciding what will be displayed on the pop-up
        when {
            //if the player lost they are displayed the lost message
            lost -> {
                add(lostMessage)
                add(doneButton)
            }
            //If the player searched and found a chip they are displayed the found chip message
            foundChip -> {
                add(foundChipMessage)
                add(closeButton)
            }
            //If the player searched and didn't find a chip then they are displayed the noFoundChip message
            // having !winner and !lost also be requirements prevents the noFoundChip message from being displayed when the player has won or lost the game
            !foundChip && !winner && !lost -> {
                add(noFoundChipMessage)
                add(closeButton)
            }
            //WHen the player wins they are displayed the winners message
            winner -> {
                add(wonMessage)
                add(doneButton)
            }
        }



    }

}

/**
 * This class is used to display the Instructions for the player at the start of the game and whenever
 * the player presses the help button
 */
class InstructionsPopUp: JDialog() {
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
        contentPane.preferredSize = Dimension(400, 420)
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
        //Adding all the messages to the pop-up
        val instructions = JLabel("<html> <p><b>BACKSTORY:</b><br>\n" +
                "    You've rigged the roulette table using magnets to always land on 17... but tripped while carrying your chips! Now they've scattered across the casino.</p>\n" +
                "    \n" +
                "    <p><b>OBJECTIVE:</b><br>\n" +
                "    • Find all 5 lost chips before the casino closes<br>\n" +
                "    • Return to the roulette table to win your prize</p>\n" +
                "    \n" +
                "    <p><b>CONTROLS:</b><br>\n" +
                "    ↑↓←→ Move between areas(Moving takes time)<br>\n" +
                "    [SEARCH] Search the room for chips(Searching takes time)<br>\n" +
                "    [TIME BAR] Shows time left to find the chips</p>")
        instructions.bounds = Rectangle(25, 25, 350, 375)
        instructions.verticalAlignment = SwingConstants.TOP
        instructions.font = baseFont
        add(instructions)

        val closeButton = JButton("Close")
        closeButton.bounds = Rectangle(150, 350, 100, 40)
        closeButton.addActionListener { this.isVisible = false } // Closes the pop-up
        add(closeButton)
    }

}



