import React from "react";
import { Appbar } from "react-native-paper";

const renderAppbarActions = (actions) => {
  if (actions) {
    return actions.map((action) => {
      return <Appbar.Action icon={action.icon} onPress={action.onPress} />;
    });
  }
};

const renderBackAction = (backAction) => {
  return backAction ? <Appbar.BackAction onPress={backAction} /> : null;
};

const CustomAppbar = ({ appBarStyle, backAction, actions }) => {
  return (
    <Appbar.Header style={[styles.appBar, appBarStyle]}>
      {renderBackAction(backAction)}
      {renderAppbarActions(actions)}
    </Appbar.Header>
  );
};

const styles = {
  appBar: {
  },
};

export default CustomAppbar;
