#include "ShipParser.h"

bool ShipParser::getOrient(char c) const
{
	if ('v' == c)
	{
		return true;
	}

	if ('h' != c)
	{
		throw InvalidInputException(incorrectInputStr);
	}

	return false;
}

ShipParser::ShipParser(std::string str) : BaseParser(str)
{

}

ShipPoint ShipParser::parse()
{
	if (str == exitStr)
	{
		throw GameExitException(exitStr);
	}
	
	if (str.size() < minLengthOfCorrectString)
	{
		throw InvalidInputException(shortStr);
	}

	char x = str[0];

	char isVertC = str[str.size() - 1];
	bool isVert = getOrient(isVertC);

	std::string y = str.substr(1, str.size() - 2);

	size_t h;
	size_t w;
	
	h = getNumFromStr(y);
	w = getNumByChar(x);

	return ShipPoint(h, w, isVert);
}

ShipParser::~ShipParser()
{

}