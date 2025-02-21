package expo.modules.sound

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import kotlin.math.PI
import kotlin.math.sin

class SoundModule : Module() {
  private var audioTrack: AudioTrack? = null
  private var isPlaying = false
  private val sampleRate = 44100

  override fun definition() = ModuleDefinition {
    Name("Sound")

    Function("initAudioEngine") {
      // No explicit initialization needed for Android
    }

    Function("start") { frequency: Double, volume: Double ->
      startTone(frequency, volume)
    }

    Function("stop") {
      stopTone()
    }
  }

  private fun startTone(frequency: Double, volume: Double) {
    val bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
    val buffer = ShortArray(bufferSize)
    
    audioTrack = AudioTrack(
      AudioManager.STREAM_MUSIC,
      sampleRate,
      AudioFormat.CHANNEL_OUT_MONO,
      AudioFormat.ENCODING_PCM_16BIT,
      bufferSize,
      AudioTrack.MODE_STREAM
    )
    
    isPlaying = true
    audioTrack?.play()
    
    Thread {
      var phase = 0.0
      val increment = 2.0 * PI * frequency / sampleRate
      while (isPlaying) {
        for (i in buffer.indices) {
          buffer[i] = (sin(phase) * volume * Short.MAX_VALUE).toInt().toShort()
          phase += increment
        }
        audioTrack?.write(buffer, 0, buffer.size)
      }
    }.start()
  }

  private fun stopTone() {
    isPlaying = false
    audioTrack?.stop()
    audioTrack?.release()
    audioTrack = null
  }
}
