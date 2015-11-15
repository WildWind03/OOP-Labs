//#include "optionparser.h"
#include "Controller.h"
#include "GameConf.h"

int main (int argc, char *argv[])
{
    std::string p1 = "ConsoleGamer";
    std::string p2 = "RandGamer";

	GameConf conf(p1, p2, 1);

	Controller controller(&conf);

	controller.beginGame();

	return 0;
}
