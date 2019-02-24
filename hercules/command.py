"""
command module
"""

COMMAND_WORDS = (
    GO,
    QUIT,
    HELP,
    TAKE,
    FLY,
    SUICIDE,
    EAT,
    GIVE,
    KILL,
) = (
    "go",
    "quit",
    "help",
    "take",
    "fly",
    "suicide",
    "eat",
    "give",
    "kill",
)


class Command(object):
    """
    Command
    """

    def __init__(self):
        self._action = None
        self._actor = None
        self._acted_upon = None
