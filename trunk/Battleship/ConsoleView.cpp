#include "ConsoleView.h"

ConsoleView::ConsoleView () : View()
{
	in.open("map.txt");	
}

void ConsoleView::printShotState(ShotState state)
{
	switch(state)
	{
		case ShotState::MISSED :
			std::cout << missedStr << std::endl;
			break;

		case ShotState::INJURED :
			std::cout << injuredStr << std::endl;
			break;

		case ShotState::DESTROYED :
			std::cout << destrStr << std::endl;
			break;

		case ShotState::ERROR :
			std::cout << wrongShotPointStr << std::endl;
			break;
	}
}

void ConsoleView::printPlacingShipError()
{
	std::cout << wrongPosForShip << std::endl;
}

void ConsoleView::printGameStartedStr()
{
	std::cout << beginGameStr << std::endl;
	in.close();
	in.open("map.txt");
}

void ConsoleView::printGameEndedStr(bool isWon)
{
	if (true == isWon)
	{
		std::cout << winStr << std::endl;
	}
	else
	{
		std::cout << loseStr << std::endl;
	}
}

ShotPoint ConsoleView::getShotPoint()
{
	char x;
	size_t h, w;

	std::cout << typeShotStr << std::endl;

	while (true)
	{
		std::string myStr;

		std::getline(std::cin, myStr);

		ShotParser sParser(myStr);

		try
		{
			ShotPoint p = sParser.parse();
			return p;
		}
		catch (std::exception & a)
		{
			printShotState(ShotState::ERROR);
			continue;
		}
	}
}

void ConsoleView::printStatistics(const Statistics & stat)
{
	std::cout << "Total Games: " << stat.getCountOfGames() << std::endl;
	std::cout << "Gamer 1 won " << stat.getG1Vic() << " games" << std::endl;
	std::cout << "Gamer 2 won " << stat.getG2Vic() << " games" << std::endl;
}

ShipPoint ConsoleView::getShipPoint(const size_t sizeOfShip)
{
	char c;
	size_t h, w;
	char orient;
	bool isVertical;

	std::cout << typeStr << sizeOfShip << std::endl;

	while (true)
	{
		std::string myStr;

		std::getline(in, myStr);

		ShipParser sParser(myStr);

		try
		{
			ShipPoint p = sParser.parse();
			return p;
		}
		catch (std::exception & a)
		{
			printPlacingShipError();
		}
	}

}

void ConsoleView::paint(const FieldView & f)
{
	if (f.getHeight() > maxHeightOfField)
	{
		throw std::runtime_error(wrongHeightStr);
	}

	std::cout << "\n  ";

	size_t counter = 0;

	char curSym = posOfAInAscii;

	for (size_t i = 0; i < f.getWidth(); ++i)
	{
		std::cout << curSym++;
	}

	for (size_t i = 0; i < f.getHeight(); ++i)
	{
		std::cout << "\n" << counter << " ";

		++counter;

		for (size_t k = 0; k < f.getWidth(); ++k)
		{
			switch(f.getCellState(i, k))
			{
				case CellState::FREE :
					std::cout << "-";
					break;

				case CellState::DESTROYED :
					std::cout << "X";
					break;

				case CellState::SHIP :
					std::cout << "*";
					break;

				case CellState::MISSED :
					std::cout << "#";
					break;

				case CellState::UNKNOWN :
					std::cout << "?";
					break;
			}

		}
	}

	std::cout << std::endl << std::endl;
}

ConsoleView::~ConsoleView()
{
	in.close();
}