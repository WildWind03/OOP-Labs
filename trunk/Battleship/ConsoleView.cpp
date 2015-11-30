#include "ConsoleView.h"

ConsoleView::ConsoleView () : View()
{
	in.open("map.txt");	
}

void ConsoleView::typeShotState(ShotState state)
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
	}
}

void ConsoleView::printGameStartedStr()
{
	
	std::cout << "The game has been begun!" << std::endl;
	in.close();
	in.open("map.txt");
}

void ConsoleView::printGameEndedStr(bool isWon)
{
	if (true == isWon)
	{
		std::cout << "Congratulations! You have won!" << std::endl;
	}
	else
	{
		std::cout << "Not bad! But you are loser!" << std::endl;
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

			break;
		}
		catch (std::exception & a)
		{
			printError(a);
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

void ConsoleView::printError(const std::exception & er)
{
	std::cout << er.what() << std::endl;
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

			break;
		}
		catch (std::exception & a)
		{
			printError(a);
			continue;
		}
	}

}

void ConsoleView::paint(const FieldView & f)
{
	std::cout << "\n  ";

	size_t height = f.getHeight();
	size_t length = f.getWidth();

	size_t counter = 0;

	char curSym = 65;

	for (char i = 0; i < length; ++i)
	{
		std::cout << curSym;
		++curSym;
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