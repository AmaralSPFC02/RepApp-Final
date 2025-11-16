import React from "react";
import { View, Text, ScrollView, TouchableOpacity } from "react-native";
import { style } from "./styles";
import { MaterialIcons, Feather } from "@expo/vector-icons";
import { useNavigation } from "@react-navigation/native";

import MoradorSaldoCard from "../../components/MoradorSaldoCard";
import DespesaCard from "../../components/DespesaCard";

export default function Financas() {
  const navigation = useNavigation();

  const moradores = [
    { nome: "João", valor: 125, tipo: "receber", foto: "https://i.imgur.com/XYZ.jpg" },
    { nome: "Maria", valor: -75, tipo: "pagar", foto: "https://i.imgur.com/XYZ2.jpg" },
    { nome: "Carlos", valor: -50, tipo: "pagar", foto: "https://i.imgur.com/XYZ3.jpg" },
  ];

  const despesas = [
    {
      titulo: "Conta de Luz",
      valor: 180,
      pagoPor: "Joao",
      data: "15/03/2024",
      porPessoa: 60,
      arquivo: "recibo_luz_marco.pdf",
      divisao: [
        { nome: "Joao", status: "pago" },
        { nome: "Maria", status: "deve", valor: 60 },
        { nome: "Carlos", status: "deve", valor: 60 },
      ],
    },
    {
      titulo: "Supermercado",
      valor: 85.5,
      pagoPor: "Maria",
      data: "12/03/2024",
      porPessoa: 28.5,
      arquivo: "nota_supermercado.jpg",
      divisao: [
        { nome: "Joao", status: "pago" },
        { nome: "Maria", status: "pago" },
        { nome: "Carlos", status: "deve", valor: 28.5 },
      ],
    },
    {
      titulo: "Internet",
      valor: 99.9,
      pagoPor: "Carlos",
      data: "10/03/2024",
      porPessoa: 33.3,
      arquivo: "recibo_internet.pdf",
      divisao: [
        { nome: "Joao", status: "deve", valor: 33.3 },
        { nome: "Maria", status: "deve", valor: 33.3 },
        { nome: "Carlos", status: "pago" },
      ],
    },
  ];

  return (
    <ScrollView style={style.container} showsVerticalScrollIndicator={false}>
      {/* Header */}
      <View style={style.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Feather name="arrow-left" size={22} color="#000" />
        </TouchableOpacity>
        <Text style={style.headerTitle}>Módulo Financeiro</Text>
        <Feather name="more-vertical" size={22} color="#000" />
      </View>

      {/* Saldo */}
      <Text style={style.sectionTitle}>Saldo dos Moradores</Text>

      <View style={style.card}>
        {moradores.map((m, index) => (
          <MoradorSaldoCard key={index} {...m} />
        ))}
      </View>

      {/* Botão adicionar */}
      <TouchableOpacity style={style.addButton}>
        <MaterialIcons name="add" size={22} color="#FFF" />
        <Text style={style.addButtonText}>Adicionar Despesa</Text>
      </TouchableOpacity>

      {/* Despesas */}
      <Text style={style.sectionTitle}>Despesas Recentes</Text>

      {despesas.map((d, index) => (
        <DespesaCard key={index} {...d} />
      ))}
    </ScrollView>
  );
}
