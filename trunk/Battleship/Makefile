all:
	g++ main.cpp MyFieldView.cpp EnemyFieldView.cpp ShotField.cpp GameConf.cpp Controller.cpp ConsoleGamer.cpp ConsoleView.cpp Field.cpp Game.cpp RandGamer.cpp Ship.cpp -std=c++11 -g -Werror -lgtest_main -lgtest -pthread -fprofile-arcs -ftest-coverage -o BattleShip.out
clean:
	rm -rf BattleShip.out
	rm -rf *.gcno
	rm -rf *.gcda
