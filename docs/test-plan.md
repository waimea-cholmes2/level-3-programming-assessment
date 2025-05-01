# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Player Movement

I will test that the player is able to move in all 4 directions

### Test Data To Use

I will use the valid moves of forward, left, right and back by pressing the forward, left, right and back buttons

### Expected Test Result

when this I press any of these buttons the current location should change to the location that was displayed as being in that direction on the available locations label

---

## Player searching Locations for chips and getting help

I will test that the search button is allowing the player to search for the chips, and that they can pull up the instructions again if needed

### Test Data To Use

I will use the valid moves of searching a location with a chip and without a chip, then i will try to use an invalid move of searching a location which has already been searched, then I will use the help button 

### Expected Test Result

When a location with a chip is searched the foundChipPopUp should be displayed and the total chips should increase, when a room without a chip is searched, the noFoundChipPopUp should be displayed, and the total chips should not increase, when the help vutton is pressed the instructions pop-up should be displayed

---

## Win/lose test

I will test that the player is able to win and lose the game

### Test Data To Use

I will collect all the chips and return to the roulette table to win the game, then I will move around until the timer runs out to lose

### Expected Test Result

when I collect all the chips and return to roulette the win pop-up should appear, and when i lose the main window should disappear and the lost pop-up should appear 

---

## Game set-up

I will test that the game sets up the correct way

### Test Data To Use

I will run the program

### Expected Test Result

When I run the program the main window should appear with the starting location being roulette, and the total chips being 0, as-well as the instructions pop-up spawning

---

## Map Boundaries

I will test that the player is unable to move off of the map

### Test Data To Use

I will move to the edge of the map then i will attempt to make the invalid move of leaving the map

### Expected Test Result

When i attempt to move off of the map any buttons that do not move to a Location should not be enabled, so it should be impossible.

---

## time level test

I will test that the time level will decrease as each move is made

### Test Data To Use

I will use valid moves to move around the map and search locations and us ethe help button

### Expected Test Result

When i make any valid moves the time level will decrease, except for when i use the help button, in which the time should not decrease

---


