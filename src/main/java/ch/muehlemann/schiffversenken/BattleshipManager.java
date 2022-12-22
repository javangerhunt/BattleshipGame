package ch.muehlemann.schiffversenken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static ch.muehlemann.schiffversenken.AttackResult.AttackResultEnum.HIT;
import static ch.muehlemann.schiffversenken.AttackResult.AttackResultEnum.MISS;
import static ch.muehlemann.schiffversenken.AttackResult.AttackResultEnum.SUNK;

public class BattleshipManager {
	private final List<Battleship> ships = new ArrayList<>();

	// to give the player the number of tries he used, the number 0 is saved into the integer variable tries
	private int tries = 0;

	// the game is over, when all battleships are sunk
	public boolean isGameOver() {
		return ships.stream().allMatch(Battleship::isSunk);
	}


	/*
	at every attack of the player, to the integer value tries gets added 1 and the program checks,
	whether a ship in the list ships has been hit, missed or even sunk
	*/
	public AttackResult attack(Position pos) {
		tries++;
		for (Battleship ship : ships) {
			if (ship.isHit(pos)) {
				if (ship.isSunk()) {
					System.out.println("ship " + ship + " sunk");
					return new AttackResult(SUNK, ship.getShipCoordinates());
				}
				return new AttackResult(HIT);
			}
		}
		return new AttackResult(MISS);


	}

	// this generates 1 ship of length 3, 2 ships of length 2, and 3 ships of length 1
	public void generateShips() {
		ships.add(generateShip(3));
		for (int i = 0; i < 2; i++) {
			ships.add(generateShip(2));
		}
		for (int i = 0; i < 3; i++) {
			ships.add(generateShip(1));
		}
	}

	/*
	this Method places the ships at random positions taking into account that they may not go over the 10X10 field
	that they do not overlap with other ships, and that they have one block distance to other ships
	*/
	private Battleship generateShip(int length) {
		Random random = new Random();
		Position atEnd;

		Battleship ship;
		boolean hasWrongLength;
		do {
			Position atBeginning = new Position(random.nextInt(10), random.nextInt(10));
			if (random.nextBoolean()) {
				// the ship will be horizontal
				atEnd = new Position((length - 1) + atBeginning.getX(), atBeginning.getY());
			} else {
				// the ship will be vertical
				atEnd = new Position(atBeginning.getX(), (length - 1) + atBeginning.getY());

			}
			hasWrongLength = atEnd.getX() > 9 || atEnd.getY() > 9;
			ship = new Battleship(atBeginning, atEnd);
		} while (hasWrongLength || hasWrongLocation(ship));


		System.out.println(ship);
		return ship;
	}

	// checks whether the ship touches, or is too close to another ship
	private boolean hasWrongLocation(Battleship ship) {
		return ships
				.stream()
				.anyMatch(otherShip -> matches(ship, calculateFieldsAroundShip(otherShip)));
	}


	// checks whether a ship contains a position of the given list
	private boolean matches(Battleship battleship, List<Position> positions) {
		return battleship
				.getShipCoordinates()
				.stream()
				.anyMatch(positions::contains);
	}

	// calculates the fields around the ship by taking the set positionsAroundPosition into account
	private List<Position> calculateFieldsAroundShip(Battleship battleship) {
		return battleship
				.getShipCoordinates()
				.stream()
				.map(this::positionsAroundPosition)
				.flatMap(Collection::stream)
				.distinct()
				.collect(Collectors.toList());
	}


	// the following provides one set with the coordinates around the ship & the ship itself
	private Set<Position> positionsAroundPosition(Position pos) {
		Set<Position> positionsAround = new HashSet<>();

		positionsAround.add(new Position(pos.getX() - 1, pos.getY()));
		positionsAround.add(new Position(pos.getX() - 1, pos.getY() - 1));
		positionsAround.add(new Position(pos.getX() - 1, pos.getY() + 1));
		positionsAround.add(new Position(pos.getX(), pos.getY() - 1));
		positionsAround.add(new Position(pos.getX(), pos.getY() + 1));
		positionsAround.add(new Position(pos.getX() + 1, pos.getY()));
		positionsAround.add(new Position(pos.getX() + 1, pos.getY() - 1));
		positionsAround.add(new Position(pos.getX() + 1, pos.getY() + 1));

		return positionsAround;
	}

	// returns the tries the player took
	public int getTries() {
		return tries;
	}

}
