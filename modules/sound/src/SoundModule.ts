import { NativeModule, requireNativeModule } from 'expo';

declare class SoundModule extends NativeModule {
  initAudioEngine(): void;
  startTone(frequency: number, volume: number): void;
  stopTone(): void;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<SoundModule>('Sound');
