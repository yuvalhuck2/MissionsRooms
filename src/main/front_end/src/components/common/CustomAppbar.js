import React from 'react';
import { StyleSheet } from 'react-native';
import { Appbar } from 'react-native-paper';

const renderAppbarActions = (actions) =>{
    return actions.map((action) => {
        return <Appbar.Action icon={action.icon} onPress={action.onPress}/>
    })
}

const CustomAppbar = ({styles, appbarActions}) => {
  return (
    <Appbar style={styles.location}>
        {renderAppbarActions(appbarActions)}
    </Appbar>
  );
};

export default CustomAppbar;
