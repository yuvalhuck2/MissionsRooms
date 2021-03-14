import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator,Appbar, List, Dialog, Portal, Searchbar } from 'react-native-paper';
import { connect } from 'react-redux';
import { suggestionChanged, deleteSuggestion, handleBack,} from '../../actions/WatchSuggestionsActions';
import { theme } from '../../core/theme';
import { WatchSuggestionsStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';

const {
  delete_suggestion,
  watch_suggestions,
  exit,
} = WatchSuggestionsStrings;

class WatchMessagesForm extends Component {
  constructor(...args) {
    super(...args);
    this.onSuggestionChanged=this.onSuggestionChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
  }

  onSuggestionChanged(suggestion) {
    this.props.suggestionChanged(suggestion);
  }

  onButtonPress() {
    const {apiKey, suggestion} = this.props;
    this.props.deleteSuggestion({apiKey, suggestion});
  }

  onBackPress(){
    const {navigation,apiKey} =this.props;
    this.props.handleBack({navigation,apiKey});
  }

  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size='large'
        styles={styles.button}
      />
    );
  }

  renderListItems(){
    const {allSuggestions} = this.props;
    return(
        allSuggestions.map((suggestion)=>
        <List.Item
        description={suggestion.suggestion}
        left={props => <List.Icon {...props} icon="lightbulb-on-outline" />}
        key={suggestion.id}
        onPress={()=>this.onSuggestionChanged(suggestion)}
        />
        )
    )
  }


  renderError() {
    const { errorMessage } = this.props;
    if (errorMessage && errorMessage !== '') {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{errorMessage}</Text>
        </View>
      );
    }
  }

  renderButton(){
    const {loading} = this.props

    return loading ? (
      this.renderSpinner()
    ) : (
      <Dialog.Actions>
        <Button
          styles={styles.button}
          onPress={this.onButtonPress}>
          {delete_suggestion}
        </Button>
      </Dialog.Actions>)
  
  }

  render() {
    const { suggestion} = this.props;
    console.log(Object.keys(suggestion))
    return (
        <KeyboardAwareScrollView style={styles.container}>
         <Appbar.Header styles={styles.bottom}>
           <Appbar.BackAction onPress={() => {this.onBackPress()}} />
         </Appbar.Header>
        <Header>{watch_suggestions}</Header>
        {this.renderListItems()}
        <Portal>
            <Dialog visible={Object.keys(suggestion).length!==0} onDismiss={()=>this.onSuggestionChanged({})}>
            <Dialog.Content>
                <Text>
                    {suggestion.suggestion}
                </Text>
                </Dialog.Content>
            {this.renderButton()}
            <Dialog.Actions>
                <Button styles={styles.button} onPress={()=>this.onSuggestionChanged({})}>{exit}</Button>
            </Dialog.Actions>
            </Dialog>
        </Portal>
        {this.renderError()}
        </KeyboardAwareScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 0,
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
  },
  button: {
    marginTop: 0,
    width:100,
  },
  row: {
    flexDirection: 'row',
    marginTop: 0,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
  },
  bottom: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
  },
});

const mapStateToProps = (state) => {
  const { search, suggestion, allSuggestions, apiKey, errorMessage, loading } = state.watchSuggestions;
  return { search, suggestion, allSuggestions, apiKey, errorMessage, loading };
};

export default connect(mapStateToProps, {
  suggestionChanged,
  deleteSuggestion,
  handleBack,
})(WatchMessagesForm);
