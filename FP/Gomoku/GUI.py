from tkinter import *
from Stones import Stones, PlaceException
from random import randint


class Board(Tk):
    def __init__(self):
        super().__init__()
        self.stone = Stones()
        self.background_color = "#%02x%02x%02x" % (183, 196, 187)
        self.secondary_color = "#%02x%02x%02x" % (242, 242, 242)
        self.color = ''
        self.color_computer = ''
        self.canvas_width = 381
        self.canvas_height = 381
        self.canvas = None
        self.label = None
        self.squares = 20

    def run(self):
        '''
        Choose the color. Calls delete()
        :return:
        '''

        self.geometry('250x200')

        self.configure(bg=self.background_color)
        self.title('Gomoku')

        # b = 0, w = 1
        color = IntVar()
        color.set(0)

        frame = Frame(self).pack(pady=15, anchor=CENTER)
        Radiobutton(frame, text='Black', variable=color, value=0, bg=self.background_color).pack()
        Radiobutton(frame, text='White', variable=color, value=1, bg=self.background_color).pack()

        Button(frame, text='Start Game', padx=20, pady=2, command=lambda: self.delete(color.get())).pack(pady=10)

        self.mainloop()

    def delete(self, color):
        '''
        Clears the screen and sets the color.
        Calls table()
        :return:
        '''
        self.geometry('500x500')
        for widget in self.winfo_children():
            widget.destroy()

        if color:
            self.color = 'White'
            self.color_computer = 'Black'
        else:
            self.color = 'Black'
            self.color_computer = 'White'
        self.table()

    def table(self):
        '''
        Draws the table. Calls add_stone()
        :return:
        '''
        self.label = Label(self, text='', bg=self.background_color)
        self.label.pack(pady=10)
        self.canvas = Canvas(self, width=self.canvas_width, height=self.canvas_height, bg=self.background_color)
        self.canvas.configure(borderwidth=0, highlightthickness=0)
        self.canvas.pack()
        self.canvas.bind('<Button-1>', self.add_stone)
        for x in range(self.squares, self.canvas_width, self.squares):
            self.canvas.create_line(x, 0, x, self.canvas_height, fill="#232323")

        for y in range(self.squares, self.canvas_height, self.squares):
            self.canvas.create_line(0, y, self.canvas_width, y, fill="#232323")

    def add_stone(self, eventorigin):
        '''
        Computes the coordinates of the selected place
        Adds to the list of coordinates, draws the stone
        and calls computer_move() and announce_winner()
        :param eventorigin: mouse on click coordinates
        :return:
        '''
        x = (eventorigin.x // 20) * 20
        y = (eventorigin.y // 20) * 20

        try:
            self.stone.add_stone(x / 20, y / 20, self.color)
            self.canvas.create_oval(x, y, x + 20, y + 20, fill=self.color)
            self.computer_move()
            self.label['text'] = ''
        except PlaceException as e:
            self.label['text'] = str(e)

        self.announce_winner()

    def computer_move(self):
        '''
        Tries to place a stone in a random place
        :return:
        '''
        x = randint(0, 18) * 20
        y = randint(0, 18) * 20

        try:
            self.stone.add_stone(x / 20, y / 20, self.color_computer)
            self.canvas.create_oval(x, y, x + 20, y + 20, fill=self.color_computer)
        except PlaceException:
            self.computer_move()

    def announce_winner(self):
        '''
        Checks if there is a winner and blocks the canvas
        :return:
        '''
        if self.stone.get_winner() is not None:
            winner = f'{str(self.stone.get_winner())} won the game'
            self.label['text'] = winner
            self.canvas.unbind('<Button-1>')






