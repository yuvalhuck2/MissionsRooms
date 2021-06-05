import React, { memo } from 'react';
import { StyleSheet, Text } from 'react-native';
import { theme } from '../../core/theme';

const Header = ({ children, headerStyle }) => <Text style={{...styles.header, ...headerStyle}}>{children}</Text>;

const styles = StyleSheet.create({
  header: {
    fontSize: 26,
    color: theme.colors.primary,
    fontWeight: 'bold',
    paddingVertical: 14,
    textAlign: 'center',
    marginTop: 100,
  },
});

export default memo(Header);
