"""
game module
"""


class Game(object):
    """
    Game
    """

    def __init__(self):
        self._parser = None
        self._hero = None
        self._people = []
        self._rooms = []
        self._things = []

    def play(self):
        """
        Entrypoint for starting a game. This method should loop until the
        game is over.
        """
        pass

    def print_welcome(self):
        """
        print_welcome
        """
        pass

    def print_help(self):
        """
        print_help
        """
        pass

    def process_command(self):
        """
        process_command
        """
        pass


if __name__ == "__main__":
    game = Game()
    game.play()
