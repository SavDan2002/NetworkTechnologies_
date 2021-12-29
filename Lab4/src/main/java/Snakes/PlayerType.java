// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Snakes.proto

package Snakes;

/**
 * <pre>
 * Тип игрока
 * </pre>
 *
 * Protobuf enum {@code snakes.PlayerType}
 */
public enum PlayerType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Живой пользователь
   * </pre>
   *
   * <code>HUMAN = 0;</code>
   */
  HUMAN(0),
  /**
   * <pre>
   * Робот, управляет своей змеёй с помощью алгоритма (вне задачи, для желающих)
   * </pre>
   *
   * <code>ROBOT = 1;</code>
   */
  ROBOT(1),
  ;

  /**
   * <pre>
   * Живой пользователь
   * </pre>
   *
   * <code>HUMAN = 0;</code>
   */
  public static final int HUMAN_VALUE = 0;
  /**
   * <pre>
   * Робот, управляет своей змеёй с помощью алгоритма (вне задачи, для желающих)
   * </pre>
   *
   * <code>ROBOT = 1;</code>
   */
  public static final int ROBOT_VALUE = 1;


  public final int getNumber() {
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static PlayerType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static PlayerType forNumber(int value) {
    switch (value) {
      case 0: return HUMAN;
      case 1: return ROBOT;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<PlayerType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      PlayerType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<PlayerType>() {
          public PlayerType findValueByNumber(int number) {
            return PlayerType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return Snakes.SnakesProto.getDescriptor().getEnumTypes().get(1);
  }

  private static final PlayerType[] VALUES = values();

  public static PlayerType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private PlayerType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:snakes.PlayerType)
}
