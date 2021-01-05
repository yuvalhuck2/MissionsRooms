import CodeInput from '@andreferi/react-native-confirmation-code-input';
import React, { Component, useEffect } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { codeChanged } from '../../actions/AuthActions';
import { authStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';

const { header, enter_code, choose_group, send_btn, no_code } = authStrings;

const data = [
  { label: 'Banana', value: 'Banana' },
  { label: 'Mango', value: 'Mango' },
  { label: 'Pear', value: 'Pear' },
];

class AuthForm extends Component {
  constructor(...args) {
    super(...args);
    this.onCodeChanged = this.onCodeChanged.bind(this);
    this.state = {
      groupsData: [],
    };
  }

  componentDidMount(){
    const { teachersData } = this.props;
    var data = [];
    console.log(teachersData)
    if (teachersData && teachersData !== []) {
      teachersData.forEach((tData) => {
        if (tData.groupType === 'BOTH') {
          data = [
            ...data,
            {
              label: `${tData.firstName} ${tData.lastName} א`,
              value: { alias: tData.alias, groupType: 'A' },
            },
            {
              label: `${tData.firstName} ${tData.lastName} ב`,
              value: { alias: tData.alias, groupType: 'B' },
            },
          ];
        } else {
          data = [
            ...data,
            {
              label: `${tData.firstName} ${tData.lastName}`,
              value: { alias: tData.alias, groupType: tData.alias.groupType },
            },
          ];
        }
      });

      this.setState({ groupsData: data });
    }
  }

  onCodeChanged(code) {
    this.props.codeChanged(code);
  }

  render() {
    const { code, group } = this.props;

    return (
      <KeyboardAwareScrollView style={styles.container}>
        <Header>{header}</Header>

        <CodeInput
          value={'12345'}
          keyboardType='numeric'
          codeLength={5}
          activeColor='rgba(148,0,211,1)'
          inactiveColor='rgba(148,0,211,1.3)'
          className='border-l-r'
          autoFocus={false}
          codeInputStyle={{ fontWeight: '800' }}
          containerStyle={{ marginTop: 30, marginBottom: 30 }}
          codeInputStyle={{ borderWidth: 1.5 }}
          cellBorderWidth={1.5}
          onFulfill={this.onCodeChanged}
        />
        <DropDownPicker
          items={this.state.groupsData}
          containerStyle={{ height: 40 }}
          style={{ backgroundColor: '#fafafa' }}
          itemStyle={{
            justifyContent: 'flex-start',
          }}
          dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
          placeholder={choose_group}
        />

        <Button mode='contained' style={styles.button}>
          {send_btn}
        </Button>

        <View style={styles.row}>
          <Text style={styles.label}>{no_code}</Text>
          <TouchableOpacity
            onPress={() => this.props.navigation.navigate('Login')}
          ></TouchableOpacity>
        </View>
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 140,
  },
  row: {
    flexDirection: 'row',
    marginTop: 15,
    alignSelf: 'center',
  },
  label: {
    fontWeight: 'bold',
    color: '#9400D3',
  },
});

const mapStateToProps = (state) => {
  const { teachersData } = state.auth;
  return { teachersData };
};

export default connect(mapStateToProps, { codeChanged })(AuthForm);
