//#include "optionparser.h"

#include "Controller.h"
#include "GameConf.h"
#include "GamerType.h"

int main (int argc, char *argv[])
{
    GamerType p1 = GamerType::consoleGamer;
    GamerType p2 = GamerType::randGamer;

	GameConf conf(p1, p2, 1);

	Controller controller(conf);

	controller.beginGame();

	return 0;
}
