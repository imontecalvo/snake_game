package model

import kotlinx.coroutines.Dispatchers
import model.events.Event
import model.events.MovementEvent
import model.events.QuitEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

class KeyListener(private val eventsChannel: Channel<Event>) {
    private val reader = KeystrokeReader()

    suspend fun listen() {
        while (true) {
            val input = reader.getLastKeyPressed()
            when (input.lowercaseChar()) {
                'w' -> eventsChannel.send(MovementEvent(0,-1))
                'a' -> eventsChannel.send(MovementEvent(-1,0))
                's' -> eventsChannel.send(MovementEvent(0,1))
                'd' ->eventsChannel.send(MovementEvent(1,0))
                'q' ->eventsChannel.send(QuitEvent())
            }

            // Small delay
            withContext(Dispatchers.IO) {
                Thread.sleep(50)
            }
        }
    }

    fun stop() {
        reader.close()
    }
}

class KeystrokeReader : KeyListener {
    private var lastKeyPressed: Char = ' '
    private val frame: JFrame = JFrame("Hidden Frame")

    init {
        frame.isUndecorated = true
        frame.setSize(1, 1)
        frame.isVisible = true
        frame.addKeyListener(this)
    }

    override fun keyTyped(e: KeyEvent) {}
    override fun keyPressed(e: KeyEvent) {
        lastKeyPressed = e.keyChar
    }

    override fun keyReleased(e: KeyEvent) {}

    fun getLastKeyPressed(): Char {
        val key = lastKeyPressed
        lastKeyPressed = ' '
        return key
    }

    fun close() {
        frame.dispose()
    }
}