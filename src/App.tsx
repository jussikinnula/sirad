import { StatusBar } from 'expo-status-bar';
import { useEffect, useState } from 'react';
import { Button, StyleSheet, View } from 'react-native';

import SoundModule from '../modules/sound';

export default function App() {
  const [isPlaying, setIsPlaying] = useState(false);

  useEffect(() => {
    SoundModule.initAudioEngine();
  }, []);

  const toggleSound = () => {
    if (isPlaying) {
      SoundModule.stop();
    } else {
      SoundModule.start(440, 0.5); // 440 Hz, 50% volume
    }
    setIsPlaying(!isPlaying);
  };

  return (
    <View style={styles.container}>
      <Button title={isPlaying ? "Stop" : "Start"} onPress={toggleSound} />
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
