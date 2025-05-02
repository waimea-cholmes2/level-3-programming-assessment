# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Player Movement

I will test that the player is able to move in all 4 directions, and buttons 
being enabled at the correct time.

### Test Data To Use

I will use the valid moves of forward, left, right and back by pressing 
the forward, left, right and back buttons.
I will also use the invalid moves of moving in a direction where there is no Location

### Expected Test Result

when I press any of these buttons the current location should change to
the location that was displayed as being in that direction on the available 
locations label, as well as the available locations label changing to display 
the available locations for the new Location. When the invalid move of moving in a
direction without a Location is attempted it should be impossible as the button going
in that direction should be disabled

---

## Player searching Locations for chips and getting help

I will test that the search button is allowing the player to 
search for the chips, and that they can pull up the instructions again if needed

### Test Data To Use

I will use the valid moves of searching a location with a chip and 
without a chip, then i will try to use an invalid move of searching a 
location which has already been searched, then I will use the help button.
I will also use the valid move of the player searching with the final move of the game

### Expected Test Result

When a location with a chip is searched the 
foundChipPopUp should be displayed and the total chips should increase, 
when a room without a chip is searched, the noFoundChipPopUp should be 
displayed, and the total chips should not increase, when the help button is
pressed the instructions pop-up should be displayed.
when a player searches with the last move of the game the pop-up should appear 
showing if they found a chip or not, then once the pop-up is closed the game 
should show a losing sate, as the player cannot search and win in the same move

---

## Win/Lose test

I will test that the player is able to win and lose the game

### Test Data To Use

I will collect all the chips and return to the roulette table to win the game, then I will move around until the timer runs out to lose

### Expected Test Result

when I collect all the chips and return to roulette the win pop-up should appear, 
and when I lose the main window should disappear and the lost pop-up should appear.
After win/lose state, a final message or UI indication should appear confirming 
the result.

---

## Game set-up and closing

I will test that the game sets up the correct way, and closes correctly 

### Test Data To Use

I will run the program, then I will close it before the game reaches a win/loss state

### Expected Test Result

When I run the program the main window should appear with the 
starting location being roulette, and the total chips being 0, 
as-well as the instructions pop-up spawning.
When the game is closed before a win/loss state is achieved the 
program should end

---

## Map Boundaries

I will test that the player is unable to move off of the map

### Test Data To Use

I will move to the edge of the map then i will attempt to make the invalid move of leaving the map

### Expected Test Result

When i attempt to move off of the map any buttons that do not move to a 
Location should not be enabled, so it should be impossible.

---

## time level test

I will test that the time level will decrease as each move is made

### Test Data To Use

I will use valid moves to move around the map and search locations and use the help button

### Expected Test Result

When I make any valid moves the time level will decrease, except for when 
I use the help button, in which the time should not decrease, 
and I will test the boundary of the player not being able to continue once the 
time level has decreased to the loosing amount

---

## Button Spamming

I will test that spamming any buttons does not cause any problems

### Test Data To Use

I will use the valid move of the help button to open the Instructions Pop-up
then I will attempt to spam the help button.
I will spam the valid moves of moving around the map
I will spam the search button

### Expected Test Result

I should be unable to interact with the help button after the first press
and until the Instructions pop-up is closed, so it will be unable to be spammed.
When moving around the map quickly the game should be able to adapt at the 
high speed of the movement.
When I spam the search button it should initially open a pop-up saying if
I found a chip or not, and I should not be able to interact with the main window 
until i close the pop-up, when I close tje pop-up the search button should be 
disabled, so it could not be spammed.

---

## Pop-up test

I will test that when a pop-up is open the player will be unable to interact with the main window

### Test Data To Use

I will attempt the invalid move of clicking on the main window while a pop-up is open

### Expected Test Result

when I click on the main window it should no0t react to the clicks

---


