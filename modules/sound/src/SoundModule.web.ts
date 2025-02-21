import { registerWebModule, NativeModule } from 'expo';

class SoundModule extends NativeModule {
  initAudioEngine() {
    console.log('initAudioEngine');
  }

  startTone(frequency: number, volume: number){
    console.log('startTone', frequency, volume);
  }

  stopTone() {
    console.log('stopTone');
  }
}

export default registerWebModule(SoundModule);
