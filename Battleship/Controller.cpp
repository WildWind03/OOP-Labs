#include "Controller.h"

Controller::Controller (GameConf  & conf)
{
	countOfRounds = conf.getCountRound();

	g1 = GamerFactory::CreateGamer(conf.getFPlayer());
	g2 = GamerFactory::CreateGamer(conf.getSPlayer());

	stat = new Statistics();

	game = new Game(*g1, *g2);
}

void Controller::beginGame()
{
	for (size_t i = 0; i < countOfRounds; ++i)
	{
		game -> newGame();

		if (game -> isG1Won())
		{
			stat -> addG1Vic();
		}
		else
		{
			stat -> addG2Vic();
		}
	}

	g1 -> onGetStatistics(*stat);
	g2 -> onGetStatistics(*stat);

	ConsoleView view;
	view.printStatistics(*stat);
}

Controller::~Controller()
{
	delete(stat);
	delete(game);
	delete(g1);
	delete(g2);
}