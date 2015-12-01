#include "Controller.h"
#include "GameConf.h"
#include "GamerType.h"
#include "ConsoleParser.h"

int main (int argc, char *argv[])
{
	ConsoleParser parser(argc, argv);

	size_t countOfRounds = parser.getCountOfRounds();

	GamerType p1 = parser.getFirstGamerType();
    GamerType p2 = parser.getSecondGamerType();

	GameConf conf(p1, p2, countOfRounds);

	Controller controller(conf);

	controller.beginGame();

	return 0;
}
