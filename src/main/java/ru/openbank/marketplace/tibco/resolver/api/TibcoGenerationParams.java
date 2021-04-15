package ru.openbank.marketplace.tibco.resolver.api;

//todo потом перейти на билдер класс
public class TibcoGenerationParams {
    private final Version version;
    private final Direction direction;
    private final TibcoEntity entity;
    private final TibcoOperation operation;
    private final TibcoMode mode;
    private final TibcoClarification clarification;
    private final String prefix;

  TibcoGenerationParams(Version version, Direction direction, TibcoEntity entity, TibcoOperation operation, TibcoMode mode, TibcoClarification clarification, String prefix) {
    this.version = version;
    this.direction = direction;
    this.entity = entity;
    this.operation = operation;
    this.mode = mode;
    this.clarification = clarification;
    this.prefix = prefix;
  }

  public Version getVersion() {
    return version;
  }

  public Direction getDirection() {
    return direction;
  }

  public TibcoEntity getEntity() {
    return entity;
  }

  public TibcoOperation getOperation() {
    return operation;
  }

  public TibcoMode getMode() {
    return mode;
  }

  public TibcoClarification getClarification() {
    return clarification;
  }

  public String getPrefix() {
    return prefix;
  }

  public static final class TibcoGenerationParamsBuilder {
    private Version version;
    private Direction direction;
    private TibcoEntity entity;
    private TibcoOperation operation;
    private TibcoMode mode;
    private TibcoClarification clarification;
    private String prefix;

    private TibcoGenerationParamsBuilder() {
    }

    public static TibcoGenerationParamsBuilder aTibcoGenerationParams() {
      return new TibcoGenerationParamsBuilder();
    }

    public TibcoGenerationParamsBuilder withVersion(Version version) {
      this.version = version;
      return this;
    }

    public TibcoGenerationParamsBuilder withDirection(Direction direction) {
      this.direction = direction;
      return this;
    }

    public TibcoGenerationParamsBuilder withEntity(TibcoEntity entity) {
      this.entity = entity;
      return this;
    }

    public TibcoGenerationParamsBuilder withOperation(TibcoOperation operation) {
      this.operation = operation;
      return this;
    }

    public TibcoGenerationParamsBuilder withMode(TibcoMode mode) {
      this.mode = mode;
      return this;
    }

    public TibcoGenerationParamsBuilder withClarification(TibcoClarification clarification) {
      this.clarification = clarification;
      return this;
    }

    public TibcoGenerationParamsBuilder withPrefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    public TibcoGenerationParams build() {
      return new TibcoGenerationParams(version, direction, entity, operation, mode, clarification, prefix);
    }
  }
}
