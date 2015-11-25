#include "ConsoleView.h"

ConsoleView::ConsoleView () : View()
{
	in.open("map.txt");	
}

SimplePoint ConsoleView::getShotPoint()
{
	char c;
	size_t h, w;

	std::cout << typeShotStr << std::endl;
	///////////
	////////// 

	while (true)
	{
		std::cin >> c;
		std::cin >> h;
		//in >> c;
		//in >> h;
		//in >> orient;

		try
		{
			w = getNumByChar(c);
			break;
		}
		catch (std::range_error & a)
		{
			std::cout << a.what() << std::endl;
			continue;
		}
	}

	return SimplePoint(h, w);	
}

void ConsoleView::printError(const std::string er) const
{
	std::cout << er << std::endl;
}

FieldPoint ConsoleView::getFieldPoint(const size_t sizeOfShip)
{
	char c;
	size_t h, w;
	char orient;
	bool isVertical;

	std::cout << typeStr << sizeOfShip << std::endl;
	///////////
	////////// 

	while (true)
	{
		//std::cin >> c;
		//std::cin >> h;
		//std::cin >> orient;
		in >> c;
		in >> h;
		in >> orient;

		try
		{
			w = getNumByChar(c);
			isVertical = getOrient(orient);
			break;
		}
		catch (std::range_error & a)
		{
			std::cout << a.what() << std::endl;
			continue;
		}
	}

	return FieldPoint(h, w, isVertical);
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

	std::cout << "\n";
}

bool ConsoleView::getOrient(char c) const
{
	if ('V' == c)
	{
		return true;
	}

	if ('H' != c)
	{
		throw std::range_error(orientErrorStr);
	}

	return false;
}

size_t ConsoleView::getNumByChar(char c) const
{
	const char posOfA = 65;
	const char posOfLastChar = 90;

	if (c < posOfA || c > posOfLastChar)
	{
		throw std::range_error(rangeErrorStr);
	}

	size_t w = (size_t) (c - posOfA);

	return w;
}

ConsoleView::~ConsoleView()
{
	in.close();
}