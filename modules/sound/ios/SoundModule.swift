import ExpoModulesCore
import AVFoundation

public class SoundModule: Module {
  var engine: AVAudioEngine = AVAudioEngine()
  var player: AVAudioPlayerNode = AVAudioPlayerNode()
  var buffer: AVAudioPCMBuffer?

  public func definition() -> ModuleDefinition {
    Name("Sound")

    Function("initAudioEngine") {
      self.initAudioEngine()
    }

    Function("start") { (frequency: Double, volume: Double) in
      self.startTone(frequency: frequency, volume: volume)
    }

    Function("stop") {
      self.stopTone()
    }
  }

  private func initAudioEngine() {
    engine.attach(player)
    engine.connect(player, to: engine.mainMixerNode, format: nil)
    try? engine.start()
  }

  private func startTone(frequency: Double, volume: Double) {
    let sampleRate = 44100.0
    let frameCount = UInt32(sampleRate)
    let format = AVAudioFormat(standardFormatWithSampleRate: sampleRate, channels: 2)!
    buffer = AVAudioPCMBuffer(pcmFormat: format, frameCapacity: frameCount)
    buffer?.frameLength = frameCount
    
    let samples = buffer!.floatChannelData!
    for i in 0..<Int(frameCount) {
      let angle = 2.0 * .pi * frequency * Double(i) / sampleRate
      let sampleValue = Float(sin(angle)) * Float(volume)
      samples[0][i] = sampleValue
      samples[1][i] = sampleValue
    }

    if !engine.isRunning {
      try? engine.start()
    }
    
    player.scheduleBuffer(buffer!, at: nil, options: .loops, completionHandler: nil)
    player.play()
  }

  private func stopTone() {
    player.stop()
  }
}
