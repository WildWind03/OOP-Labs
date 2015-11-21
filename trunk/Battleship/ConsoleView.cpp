#include "ConsoleView.h"

ConsoleView::ConsoleView () : View()
{
	
}

void ConsoleView::printInputError() const
{
	std::cout << inputErrorStr << std::endl;
}

FieldPoint ConsoleView::getFieldPoint(size_t sizeOfShip)
{
	char c;
	size_t h, w;
	char orient;
	bool isVertical;

	std::cout << typeStr << sizeOfShip << std::endl;

	while (true)
	{
		std::cin >> c;
		std::cin >> h;
		std::cin >> orient;

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

void ConsoleView::paint(Field *f)
{
	std::cout << "\n  ";

	size_t height = f -> getHeight();
	size_t length = f -> getWidth();

	size_t counter = 0;

	char curSym = 65;

	for (char i = 0; i < length; ++i)
	{
		std::cout << curSym;
		++curSym;
	}

	for (size_t i = 0; i < f -> getHeight(); ++i)
	{
		std::cout << "\n" << counter << " ";

		++counter;

		for (size_t k = 0; k < f -> getWidth(); ++k)
		{
			std::string state = f -> getStateOfCell(i, k);

			if ("FREE" == state)
			{
				std::cout << "-";
			}

			if ("BUSY" == state)
			{
				std::cout << "*";
			}

			if ("DESTROYED" == state)
			{
				std::cout << "X";
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

}